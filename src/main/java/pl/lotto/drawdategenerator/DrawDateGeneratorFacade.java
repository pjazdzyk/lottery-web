package pl.lotto.drawdategenerator;

import java.time.*;

public class DrawDateGeneratorFacade {

    private final DayOfWeek defDrawDayOfWeek = DayOfWeek.SATURDAY;
    private final LocalTime defDrawTime = LocalTime.of(20, 0);
    private final DrawDateGenerator drawDateGenerator = new DrawDateGenerator(defDrawDayOfWeek, defDrawTime);

    public LocalDateTime getDrawDate() {
        return drawDateGenerator.getDrawDate();
    }

    public LocalDateTime getCurrentDateAndTime() {
        return CurrentTimeGenerator.getCurrentDateAndTime();
    }

    public void setNewDrawDateAndTime(DayOfWeek drawDayOfWeek, LocalTime drawTime) {
        drawDateGenerator.setNewDrawDateAndTime(drawDayOfWeek, drawTime);
        //TODO future token database must be updated (only tokens which did not participated in draw yet)
    }

}

