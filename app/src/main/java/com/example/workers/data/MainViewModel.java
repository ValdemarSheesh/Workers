package com.example.workers.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainViewModel extends AndroidViewModel {

    private static WorkersDatabase database;
    private LiveData<List<Worker>> workers;


    public MainViewModel(@NonNull Application application) {
        super(application);
        database = WorkersDatabase.getInstance(application);
        workers = database.workerDao().getAllWorkers();
    }

    public LiveData<List<Worker>> getWorkers() {
        return workers;
    }

    public void insertWorker (Worker worker) {
        new InsertTask().execute(worker);
    }

    public void deleteWorker(Worker worker) {
        new DeleteTask().execute(worker);
    }

    public void deleteAllWorkers() {
        new DeleteAllTask().execute();
    }

    public LiveData<List<String>> getSpecialities() {
        try {
            return new GetSpecialities().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LiveData<List<Worker>> getWorkersBySpeciality(String speciality) {
        try {
            return new GetWorkersBySpecialityTask().execute(speciality).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class InsertTask extends AsyncTask<Worker, Void, Void> {
        @Override
        protected Void doInBackground(Worker... workers) {
            if (workers != null && workers.length > 0) {
                database.workerDao().insertWorker(workers[0]);
            }
            return null;
        }
    }

    private static class DeleteTask extends AsyncTask<Worker, Void, Void> {
        @Override
        protected Void doInBackground(Worker... workers) {
            if (workers != null && workers.length > 0) {
                database.workerDao().deleteWorker(workers[0]);
            }
            return null;
        }
    }

    private static class DeleteAllTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            database.workerDao().deleteAllWorkers();
            return null;
        }
    }

    private static class GetSpecialities extends AsyncTask<Void, Void, LiveData<List<String>>> {
        @Override
        protected LiveData<List<String>> doInBackground(Void... voids) {
            return database.workerDao().getSpecialities();
        }
    }

    private static class GetWorkersBySpecialityTask extends AsyncTask<String, Void, LiveData<List<Worker>>> {
        @Override
        protected LiveData<List<Worker>> doInBackground(String... strings) {
            if (strings != null && strings.length > 0) {
                return database.workerDao().getWorkersBySpeciality(strings[0]);
            }
            return null;
        }
    }
}
























