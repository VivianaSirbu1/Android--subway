package com.example.transport;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface MetrouDao {
    @Query("select* from metrou")
    List<Metrou> getAll();

    @Insert
    long insert(Metrou metrou);

    @Update
    int update(Metrou metrou);

    @Delete
    int delete(Metrou metrou);

    @Query("delete from metrou")
    void deleteAll();
}
