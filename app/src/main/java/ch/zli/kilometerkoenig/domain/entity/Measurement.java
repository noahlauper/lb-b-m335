package ch.zli.kilometerkoenig.domain.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Time;
import java.time.Instant;

@Entity(tableName = "Measurement")
public class Measurement {

    @PrimaryKey
    private Long id;
    @ColumnInfo(name = "startTime")
    private String startTime;
    @ColumnInfo(name = "endTime")
    private String endTime;
    @ColumnInfo(name = "steps")
    private int steps;

    @ColumnInfo(name = "lvlPoints")
    private int lvlPoints;

    public Measurement(Long id, String startTime, String endTime, int steps, int lvlPoints) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.steps = steps;
        this.lvlPoints = lvlPoints;
    }

    public Measurement() {    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
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
