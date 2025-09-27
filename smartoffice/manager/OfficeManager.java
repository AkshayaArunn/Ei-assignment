package org.example.smartoffice.manager;

import org.example.smartoffice.factory.RoomFactory;
import org.example.smartoffice.model.Room;
import org.example.smartoffice.util.LoggerUtil;

//import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
//import java.util.UUID;

public class OfficeManager {
    private static OfficeManager instance;
    private final Map<Integer, Room> rooms = new ConcurrentHashMap<>();
    private final BookingService bookingService;
    private final Logger logger = LoggerUtil.getLogger();

    private int defaultRoomCapacity = 10;

    private OfficeManager() {
        this.bookingService = new BookingService(this);
    }

    public static synchronized OfficeManager getInstance() {
        if (instance == null) instance = new OfficeManager();
        return instance;
    }

    public synchronized void initRooms(int count) {
        rooms.clear();
        for (int i = 1; i <= count; i++) {
            rooms.put(i, RoomFactory.createRoom(i, defaultRoomCapacity));
        }
        logger.info("Initialized " + count + " rooms");
    }

    public Room getRoom(int id) {
        return rooms.get(id);
    }

    public Collection<Room> getAllRooms() {
        return rooms.values();
    }

    public BookingService getBookingService() { return bookingService; }

    public void addObserverToAllRooms(org.example.smartoffice.observer.OccupancyObserver obs) {
        for (Room r : rooms.values()) r.addObserver(obs);
    }

    public synchronized void setRoomCapacity(int roomId, int capacity) {
        Room r = rooms.get(roomId);
        if (r != null) r.setCapacity(capacity);
    }

    public int getDefaultRoomCapacity() { return defaultRoomCapacity; }
    public void setDefaultRoomCapacity(int c) { this.defaultRoomCapacity = c; }
    
    public void addOccupants(int roomId, int num) {
        Room r = rooms.get(roomId);
        if (r == null) throw new IllegalArgumentException("No room " + roomId);
        r.addOccupants(num);
        bookingService.onOccupancyChanged(r);
    }

    public void removeOccupants(int roomId, int num) {
        Room r = rooms.get(roomId);
        if (r == null) throw new IllegalArgumentException("No room " + roomId);
        r.removeOccupants(num);
        bookingService.onOccupancyChanged(r);
    }

    public void shutdown() {
        bookingService.shutdown();
        logger.info("OfficeManager shutdown.");
    }
}

