package org.example.smartoffice.commands;

import org.example.smartoffice.manager.OfficeManager;
import org.example.smartoffice.model.Booking;
import org.example.smartoffice.util.TimeUtils;
import org.example.smartoffice.util.ValidationException;

import java.time.LocalDateTime;

public class BookRoomCommand implements Command {
    private final OfficeManager manager;

    public BookRoomCommand(OfficeManager m) { this.manager = m; }

    @Override
    public void execute(String[] args) throws Exception {
      
        if (args.length < 4) {
            throw new ValidationException("Usage: book <roomId> <HH:mm> <durationMinutes>");
        }
        int roomId = Integer.parseInt(args[1]);
        String hhmm = args[2];
        int duration = Integer.parseInt(args[3]);
        LocalDateTime start = TimeUtils.parseTodayOrTomorrow(hhmm);
        Booking b = manager.getBookingService().createBooking(roomId, start, duration);
        System.out.println("Booking created: " + b.getId());
    }
}
