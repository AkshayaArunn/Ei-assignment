package org.example.smartoffice.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Booking {
    private final UUID id;
    private final int roomId;
    private final LocalDateTime start;
    private final LocalDateTime end;
    private volatile BookingStatus status;
    private final LocalDateTime createdAt;
    private volatile boolean occupied = false;

    public Booking(int roomId, LocalDateTime start, LocalDateTime end) {
        this.id = UUID.randomUUID();
        this.roomId = roomId;
        this.start = start;
        this.end = end;
        this.status = BookingStatus.ACTIVE;
        this.createdAt = LocalDateTime.now();
    }

    public UUID getId() { return id; }
    public int getRoomId() { return roomId; }
    public LocalDateTime getStart() { return start; }
    public LocalDateTime getEnd() { return end; }
    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public synchronized boolean isOccupied() { return occupied; }
    public synchronized void setOccupied(boolean b) { this.occupied = b; }

    @Override
    public String toString() {
        return String.format("Booking{id=%s, room=%d, start=%s, end=%s, status=%s, occupied=%s}",
                id.toString(), roomId, start.toString(), end.toString(), status, occupied);
    }
}
