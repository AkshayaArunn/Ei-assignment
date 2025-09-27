package org.example.smartoffice.commands;

import org.example.smartoffice.manager.OfficeManager;
import org.example.smartoffice.util.ValidationException;

public class ConfigCommand implements Command {
    private final OfficeManager manager;

    public ConfigCommand(OfficeManager m) { this.manager = m; }

    @Override
    public void execute(String[] args) throws Exception {
        
        if (args.length < 2) throw new ValidationException("Usage: config <option> ...");

        String opt = args[1];
        if ("roomCount".equalsIgnoreCase(opt)) {
            int n = Integer.parseInt(args[2]);
            manager.initRooms(n);
            System.out.println("Initialized " + n + " rooms.");
        } else if ("roomCapacity".equalsIgnoreCase(opt)) {
            int rid = Integer.parseInt(args[2]);
            int cap = Integer.parseInt(args[3]);
            manager.setRoomCapacity(rid, cap);
            System.out.println("Room " + rid + " capacity set to " + cap);
        } else if ("autoReleaseSeconds".equalsIgnoreCase(opt)) {
            long s = Long.parseLong(args[2]);
            manager.getBookingService().setAutoReleaseDelaySeconds(s);
            System.out.println("Auto-release set to " + s + " seconds");
        } else if ("occupancyThreshold".equalsIgnoreCase(opt)) {
            int th = Integer.parseInt(args[2]);
            manager.getBookingService().setOccupancyThreshold(th);
            System.out.println("Occupancy threshold set to " + th);
        } else {
            throw new ValidationException("Unknown config option: " + opt);
        }
    }
}
