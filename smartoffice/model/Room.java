package org.example.smartoffice.model;
import org.example.smartoffice.observer.OccupancyObserver;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Room {
    private final int id;
    private int capacity;
    private int occupants = 0;
    private final List<OccupancyObserver> observers = new CopyOnWriteArrayList<>();

    public Room(int id, int capacity) {
        this.id = id;
        this.capacity = capacity;
    }

    public int getId() { return id; }
    public synchronized int getOccupants() { return occupants; }
    public synchronized int getCapacity() { return capacity; }

    public synchronized void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void addObserver(OccupancyObserver o) {
        observers.add(o);
    }

    public void removeObserver(OccupancyObserver o) {
        observers.remove(o);
    }

    private void notifyObservers() {
        for (OccupancyObserver o : observers) {
            try {
                o.onOccupancyChanged(this);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public synchronized void addOccupants(int n) {
        if (n <= 0) throw new IllegalArgumentException("Add positive occupants");
        this.occupants = Math.min(this.capacity, this.occupants + n);
        notifyObservers();
    }

    public synchronized void removeOccupants(int n) {
        if (n <= 0) throw new IllegalArgumentException("Remove positive occupants");
        this.occupants = Math.max(0, this.occupants - n);
        notifyObservers();
    }

    @Override
    public String toString() {
        return String.format("Room{id=%d, capacity=%d, occupants=%d}", id, capacity, occupants);
    }
}

