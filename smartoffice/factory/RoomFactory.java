package org.example.smartoffice.factory;

import org.example.smartoffice.model.Room;

public final class RoomFactory {
    private RoomFactory() {}

    public static Room createRoom(int id, int capacity) {
        return new Room(id, capacity);
    }
}
