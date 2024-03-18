package ch.zli.kilometerkoenig.domain.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ch.zli.kilometerkoenig.domain.entity.Measurement;

@Dao
public interface MeasurementDao {

    @Query("Select * from Measurement")
    List<Measurement> getAll();

    @Insert
    void insertAll(Measurement... measurements);
}
