package org.example.smartoffice.commands;

import org.example.smartoffice.manager.OfficeManager;
import org.example.smartoffice.exporter.BookingExporter;
import org.example.smartoffice.exporter.CSVBookingExporter;
import org.example.smartoffice.model.Booking;
import org.example.smartoffice.util.ConsoleColors;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportCommand implements Command {
    private final OfficeManager manager;

    public ExportCommand(OfficeManager m) {
        this.manager = m;
    }

    @Override
    public void execute(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println(ConsoleColors.YELLOW + "Usage: export csv [filename]" + ConsoleColors.RESET);
            return;
        }

        String format = args[1].toLowerCase();

        if ("csv".equals(format)) {
            BookingExporter exporter = new CSVBookingExporter();
            List<Booking> all = manager.getBookingService().getAllBookingsSorted();
            String output = exporter.export(all);

            String filename = (args.length >= 3) ? args[2] : "bookings.csv";
            try (FileWriter fw = new FileWriter(filename)) {
                fw.write(output);
            } catch (IOException e) {
                System.out.println(ConsoleColors.RED + "Failed to write file: " + e.getMessage() + ConsoleColors.RESET);
                return;
            }

            System.out.println(ConsoleColors.GREEN + "Exported bookings to " + filename + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.RED + "Unsupported export format: " + format + ConsoleColors.RESET);
        }
    }
}
