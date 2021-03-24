package com.example.transport;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Metrou.class, Conductor.class}, version = 2, exportSchema = false)
public abstract  class MetrouDB extends RoomDatabase  {

    public static final String DB_NAME = "metrou.db";
    private static MetrouDB instanta;

    public static MetrouDB getInstanta(Context context)
    {
        if (instanta==null)
            instanta = Room.databaseBuilder(context, MetrouDB.class, DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();

        return instanta;
    }

    public abstract MetrouDao getMetrouDao();

    public abstract  ConductoriDao getConsuctoriDao();
}
