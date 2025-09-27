package org.example.smartoffice.commands;

import org.example.smartoffice.manager.OfficeManager;
import org.example.smartoffice.util.ValidationException;

public class AddOccupantCommand implements Command {
    private final OfficeManager manager;

    public AddOccupantCommand(OfficeManager m) { this.manager = m; }

    @Override
    public void execute(String[] args) throws Exception {
       
        if (args.length < 3) throw new ValidationException("Usage: addOccupant <roomId> <count>");
        int roomId = Integer.parseInt(args[1]);
        int cnt = Integer.parseInt(args[2]);
        manager.addOccupants(roomId, cnt);
        System.out.println("Added " + cnt + " occupant(s) to room " + roomId);
    }
}

