package org.example.smartoffice.commands;

import org.example.smartoffice.manager.OfficeManager;
import org.example.smartoffice.util.ValidationException;

import java.util.UUID;

public class CancelBookingCommand implements Command {
    private final OfficeManager manager;

    public CancelBookingCommand(OfficeManager m) { this.manager = m; }

    @Override
    public void execute(String[] args) throws Exception {

        if (args.length < 2) throw new ValidationException("Usage: cancel <bookingId>");
        UUID id = UUID.fromString(args[1]);
        manager.getBookingService().cancelBooking(id);
        System.out.println("Cancelled booking: " + id);
    }
}

