package pl.lotto.drawdategenerator;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DrawDateGeneratorFacade {

//    private final DayOfWeek defDrawDayOfWeek = DayOfWeek.SATURDAY;
//    private final LocalTime defDrawTime = LocalTime.of(20, 0);
//    private final Clock clock = Clock.systemUTC();
//    private final DrawDateGenerator drawDateGenerator = new DrawDateGenerator(defDrawDayOfWeek, defDrawTime, clock);

    private final DrawDateGenerator drawDateGenerator;
    private final CurrentTimeGenerator currentTimeGenerator;

    public DrawDateGeneratorFacade(DrawDateGenerator drawDateGenerator, CurrentTimeGenerator currentTimeGenerator) {
        this.drawDateGenerator = drawDateGenerator;
        this.currentTimeGenerator = currentTimeGenerator;
    }

    public LocalDateTime getDrawDate() {
        LocalDateTime currentDateTime = currentTimeGenerator.getCurrentDateAndTime();
        return drawDateGenerator.getDrawDate(currentDateTime);
    }

    public LocalDateTime getCurrentDateAndTime() {
        return CurrentTimeGenerator.getCurrentDateAndTime();
    }

    public void setNewDrawDateAndTime(DayOfWeek drawDayOfWeek, LocalTime drawTime) {
        drawDateGenerator.setNewDrawDateAndTime(drawDayOfWeek, drawTime);
        //TODO future token database must be updated (only tokens which did not participated in draw yet)
    }

}

