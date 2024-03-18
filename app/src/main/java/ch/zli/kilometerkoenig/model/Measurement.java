package ch.zli.kilometerkoenig.model;

import java.time.Instant;

public class Measurement {

    private Long id;
    private Instant startTime;
    private Instant endTime;
    private int steps;
    private int lvlPoints;

    public Measurement(Long id, Instant startTime, Instant endTime, int steps, int lvlPoints) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.steps = steps;
        this.lvlPoints = lvlPoints;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int getLvlPoints() {
        return lvlPoints;
    }

    public void setLvlPoints(int lvlPoints) {
        this.lvlPoints = lvlPoints;
    }
}
