package org.example.smartoffice.exporter;

import org.example.smartoffice.model.Booking;
import java.util.List;

public interface BookingExporter {
    String export(List<Booking> bookings);
}

