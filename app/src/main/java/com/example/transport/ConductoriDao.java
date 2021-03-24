package com.example.transport;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface ConductoriDao {
    @Query("select * from conductori")
    List<Conductor> getAll();

    @Insert
    long insert(Conductor conductor);

    @Update
    int update(Conductor conductor);

    @Delete
    int delete(Conductor conductor);

    @Query("delete from conductori")
    void deleteAll();
}
