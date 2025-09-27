package org.example.smartoffice.exporter;

import org.example.smartoffice.model.Booking;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;

public class CSVBookingExporter implements BookingExporter {
    @Override
    public String export(List<Booking> bookings) {
        String header = "id,room,start,end,status,occupied";
        String body = bookings.stream()
                .sorted(Comparator.comparing(Booking::getStart)) 
                .map(b -> String.format("%s,%d,%s,%s,%s,%s",
                        b.getId(), b.getRoomId(), b.getStart(), b.getEnd(), b.getStatus(), b.isOccupied()))
                .collect(Collectors.joining("\n"));
        return header + "\n" + body;
    }
}
