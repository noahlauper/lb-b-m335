package ch.zli.kilometerkoenig.domain;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import ch.zli.kilometerkoenig.domain.dao.MeasurementDao;
import ch.zli.kilometerkoenig.domain.entity.*;

@Database(entities = {ch.zli.kilometerkoenig.domain.dao.entity.Measurement.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static final String  DATABASE_NAME = "measurements";

    private static AppDatabase instance;

    public abstract MeasurementDao measurementDao();

    public static AppDatabase getInstance(Context context) {
        if(instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME).build();
                }
            }
        }
        return instance;
    }
}
