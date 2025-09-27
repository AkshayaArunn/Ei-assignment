package org.example.smartoffice.observer;

import org.example.smartoffice.model.Room;

public interface OccupancyObserver {
    void onOccupancyChanged(Room room);
}
