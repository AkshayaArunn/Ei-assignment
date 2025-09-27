package org.example.smartoffice.observer;


import org.example.smartoffice.model.Room;
import java.util.logging.Logger;
import org.example.smartoffice.util.LoggerUtil;

public class LightController implements OccupancyObserver {
    private final Logger logger = LoggerUtil.getLogger();

    @Override
    public void onOccupancyChanged(Room room) {
        if (room.getOccupants() > 0) {
            logger.info("[LightController] Room " + room.getId() + ": Lights ON");
        } else {
            logger.info("[LightController] Room " + room.getId() + ": Lights OFF");
        }
    }
}

