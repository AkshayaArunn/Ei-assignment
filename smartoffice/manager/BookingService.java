package org.example.smartoffice.manager;

import org.example.smartoffice.model.Booking;
import org.example.smartoffice.model.BookingStatus;
import org.example.smartoffice.util.TimeUtils;
import org.example.smartoffice.util.ValidationException;
import org.example.smartoffice.model.Room;
import org.example.smartoffice.util.LoggerUtil;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Logger;

public class BookingService {
    private final Map<UUID, Booking> bookings = new ConcurrentHashMap<>();
    private final Map<UUID, ScheduledFuture<?>> scheduledAutoReleases = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final OfficeManager officeManager;
    private final Logger logger = LoggerUtil.getLogger();
    private volatile long autoReleaseDelaySeconds = 300; 
    private volatile int occupancyThreshold = 1;

    public BookingService(OfficeManager officeManager) {
        this.officeManager = officeManager;
    }

    public void setAutoReleaseDelaySeconds(long seconds) {
        this.autoReleaseDelaySeconds = seconds;
    }

    public void setOccupancyThreshold(int threshold) {
        this.occupancyThreshold = Math.max(1, threshold);
    }

    public Booking createBooking(int roomId, LocalDateTime start, int durationMinutes) throws ValidationException {
        Room room = officeManager.getRoom(roomId);
        if (room == null) throw new ValidationException("Room " + roomId + " does not exist");
        if (durationMinutes <= 0) throw new ValidationException("Duration must be positive");

        LocalDateTime end = start.plusMinutes(durationMinutes);

        for (Booking b : bookings.values()) {
            if (b.getRoomId() == roomId && b.getStatus() == BookingStatus.ACTIVE) {
                if (TimeUtils.intervalsOverlap(b.getStart(), b.getEnd(), start, end)) {
                    throw new ValidationException("Booking overlaps with existing booking " + b.getId());
                }
            }
        }

        Booking booking = new Booking(roomId, start, end);
        bookings.put(booking.getId(), booking);
        logger.info("Created booking: " + booking);

        scheduleAutoRelease(booking);
        return booking;
    }

    public void cancelBooking(UUID bookingId) throws ValidationException {
        Booking b = bookings.get(bookingId);
        if (b == null) throw new ValidationException("Booking not found: " + bookingId);
        if (b.getStatus() != BookingStatus.ACTIVE) throw new ValidationException("Booking not active: " + b.getStatus());
        b.setStatus(BookingStatus.CANCELLED);
        cancelScheduledRelease(b.getId());
        logger.info("Booking cancelled: " + b.getId());
    }

    public Booking getBooking(UUID id) {
        return bookings.get(id);
    }

    public List<Booking> getBookingsForRoom(int roomId) {
        List<Booking> list = new ArrayList<>();
        for (Booking b : bookings.values()) {
            if (b.getRoomId() == roomId) list.add(b);
        }
        return list;
    }

    public Optional<Booking> findActiveBookingForRoomAt(int roomId, LocalDateTime at) {
        return bookings.values().stream()
                .filter(b -> b.getRoomId() == roomId && b.getStatus() == BookingStatus.ACTIVE)
                .filter(b -> !at.isBefore(b.getStart()) && at.isBefore(b.getEnd()))
                .findFirst();
    }

    public void onOccupancyChanged(Room room) {
    
        LocalDateTime now = LocalDateTime.now();
        Optional<Booking> active = findActiveBookingForRoomAt(room.getId(), now);
        active.ifPresent(b -> {
            if (room.getOccupants() >= occupancyThreshold && !b.isOccupied()) {
                b.setOccupied(true);
                cancelScheduledRelease(b.getId());
                logger.info("Booking " + b.getId() + " marked as occupied.");
            }
        });
    }

    private void scheduleAutoRelease(Booking booking) {
        Runnable task = () -> {
            try {
                Booking b = bookings.get(booking.getId());
                if (b == null) return;
                if (b.getStatus() != BookingStatus.ACTIVE) return;
                if (!b.isOccupied()) {
                    b.setStatus(BookingStatus.AUTO_RELEASED);
                    logger.info("Auto-released booking: " + b.getId());
                }
            } catch (Exception ex) {
                logger.severe("Error in auto-release task: " + ex.getMessage());
            }
        };

        ScheduledFuture<?> future = scheduler.schedule(task, autoReleaseDelaySeconds, TimeUnit.SECONDS);
        scheduledAutoReleases.put(booking.getId(), future);
    }

    private void cancelScheduledRelease(UUID bookingId) {
        ScheduledFuture<?> f = scheduledAutoReleases.remove(bookingId);
        if (f != null) f.cancel(false);
    }

    public void shutdown() {
        scheduler.shutdownNow();
    }
    public List<Booking> getAllBookingsSorted() {
    List<Booking> list = new ArrayList<>(bookings.values());
        list.sort(Comparator.comparing(Booking::getStart));
        return list;
    }

}

