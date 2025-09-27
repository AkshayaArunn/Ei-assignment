package org.example.smartoffice.observer;

import org.example.smartoffice.model.Room;
import java.util.logging.Logger;
import org.example.smartoffice.util.LoggerUtil;

public class ACController implements OccupancyObserver {
    private final Logger logger = LoggerUtil.getLogger();

    @Override
    public void onOccupancyChanged(Room room) {
        if (room.getOccupants() > 0) {
            logger.info("[ACController] Room " + room.getId() + ": AC set to COMFORT");
        } else {
            logger.info("[ACController] Room " + room.getId() + ": AC set to ECO");
        }
    }
}
