package com.example.workers.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Worker.class, version = 5, exportSchema = false)
public abstract class WorkersDatabase extends RoomDatabase {

    private static WorkersDatabase database;
    public static final String DB_NAME = "workers.db";
    public static final Object LOCK = new Object();

    public static WorkersDatabase getInstance(Context context) {
        synchronized (LOCK) {
            if (database == null) {
                database = Room.databaseBuilder(context, WorkersDatabase.class, DB_NAME).build();
            }
        }
        return database;
    }

    public abstract WorkerDao workerDao();
}
