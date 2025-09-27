package org.example.smartoffice.commands;

import org.example.smartoffice.manager.OfficeManager;
import org.example.smartoffice.model.Booking;
import org.example.smartoffice.util.ConsoleColors;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public class StatusCommand implements Command {
    private final OfficeManager manager;

    public StatusCommand(OfficeManager m) { this.manager = m; }

    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            System.out.println(ConsoleColors.YELLOW + "Usage: status all | status <roomId> | status booking <uuid>" + ConsoleColors.RESET);
            return;
        }

        String option = args[1].toLowerCase();

        switch (option) {
            case "all":
                printAll();
                break;
            case "booking":
                if (args.length < 3) {
                    System.out.println(ConsoleColors.YELLOW + "Usage: status booking <uuid>" + ConsoleColors.RESET);
                } else {
                    printBooking(args[2]);
                }
                break;
            default:
                try {
                    int roomId = Integer.parseInt(option);
                    printRoom(roomId);
                } catch (NumberFormatException e) {
                    System.out.println(ConsoleColors.RED + "Invalid option: " + args[1] + ConsoleColors.RESET);
                }
        }
    }

    private void printAll() {
        System.out.println(ConsoleColors.CYAN + ConsoleColors.BOLD + "\n=== Rooms ===" + ConsoleColors.RESET);
        manager.getAllRooms().forEach(r -> {
            System.out.println("Room " + r.getId() + " | Capacity: " + r.getCapacity() + " | Occupants: " + r.getOccupants());
        });

        System.out.println(ConsoleColors.CYAN + ConsoleColors.BOLD + "\n=== Bookings ===" + ConsoleColors.RESET);
        boolean found = false;
        for (var room : manager.getAllRooms()) {
            List<Booking> bookings = manager.getBookingService().getBookingsForRoom(room.getId());
            if (!bookings.isEmpty()) {
                found = true;
                bookings.forEach(this::printBookingLine);
            }
        }
        if (!found) {
            System.out.println(ConsoleColors.YELLOW + "No bookings yet." + ConsoleColors.RESET);
        }
    }

    private void printRoom(int roomId) {
        var room = manager.getRoom(roomId);
        if (room == null) {
            System.out.println(ConsoleColors.RED + " Room " + roomId + " does not exist." + ConsoleColors.RESET);
            return;
        }
        System.out.println("Room " + room.getId() + " | Capacity: " + room.getCapacity() + " | Occupants: " + room.getOccupants());
        List<Booking> bookings = manager.getBookingService().getBookingsForRoom(roomId);
        if (bookings.isEmpty()) {
            System.out.println(ConsoleColors.YELLOW + "No bookings for this room." + ConsoleColors.RESET);
        } else {
            bookings.forEach(this::printBookingLine);
        }
    }

    private void printBooking(String uuidStr) {
        try {
            UUID id = UUID.fromString(uuidStr);
            Booking b = manager.getBookingService().getBooking(id);
            if (b == null) {
                System.out.println(ConsoleColors.RED + "Booking not found: " + uuidStr + ConsoleColors.RESET);
            } else {
                printBookingLine(b);
            }
        } catch (IllegalArgumentException e) {
            System.out.println(ConsoleColors.RED + "Invalid booking UUID format." + ConsoleColors.RESET);
        }
    }

    private void printBookingLine(Booking b) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM HH:mm");
        String occ = b.isOccupied() ? ConsoleColors.GREEN + "Occupied" + ConsoleColors.RESET
                                    : ConsoleColors.YELLOW + "Pending" + ConsoleColors.RESET;
        System.out.printf("Booking %s | Room %d | %s to %s | %s | Status: %s%n",
                b.getId().toString().substring(0, 8),
                b.getRoomId(),
                b.getStart().format(fmt),
                b.getEnd().format(fmt),
                occ,
                b.getStatus());
    }
}
