package com.example.workers.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WorkerDao {
    @Insert
    void insertWorker(Worker worker);

    @Delete
    void deleteWorker(Worker worker);

    @Query("DELETE FROM workers")
    void deleteAllWorkers();

    @Query("SELECT * FROM workers")
    LiveData<List<Worker>> getAllWorkers();

    @Query("SELECT DISTINCT speciality FROM workers")
    LiveData<List<String>> getSpecialities();

    @Query("SELECT * FROM workers WHERE speciality == :spec")
    LiveData<List<Worker>> getWorkersBySpeciality(String spec);
}
