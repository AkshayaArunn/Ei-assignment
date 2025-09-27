package org.example.smartoffice.commands;

import org.example.smartoffice.manager.OfficeManager;

public class ExitCommand implements Command {
    private final OfficeManager manager;
    private final Runnable stopSignal;

    public ExitCommand(OfficeManager m, Runnable stopSignal) {
        this.manager = m;
        this.stopSignal = stopSignal;
    }

    @Override
    public void execute(String[] args) {
        manager.shutdown();
        stopSignal.run();
        System.out.println("Exiting SmartOffice. Goodbye!");
    }
}
