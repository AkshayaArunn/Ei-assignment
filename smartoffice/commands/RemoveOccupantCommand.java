package org.example.smartoffice.commands;

import org.example.smartoffice.manager.OfficeManager;
import org.example.smartoffice.util.ValidationException;

public class RemoveOccupantCommand implements Command {
    private final OfficeManager manager;

    public RemoveOccupantCommand(OfficeManager m) { this.manager = m; }

    @Override
    public void execute(String[] args) throws Exception {
        if (args.length < 3) throw new ValidationException("Usage: removeOccupant <roomId> <count>");
        int roomId = Integer.parseInt(args[1]);
        int cnt = Integer.parseInt(args[2]);
        manager.removeOccupants(roomId, cnt);
        System.out.println("Removed " + cnt + " occupant(s) from room " + roomId);
    }
}
