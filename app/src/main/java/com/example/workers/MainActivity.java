package com.example.workers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workers.adapters.SpecialityAdapter;
import com.example.workers.data.MainViewModel;
import com.example.workers.data.Worker;
import com.example.workers.utils.JSONLoader;
import com.example.workers.utils.JSONUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<JSONObject> {

    private String url = "https://gitlab.65apps.com/65gb/static/raw/master/testTask.json";
    private LoaderManager loaderManager;
    private static final int LOADER_ID = 99;
    private MainViewModel viewModel;
    private RecyclerView recyclerViewSpeciality;
    private SpecialityAdapter adapter;
    public static final ArrayList<Worker> workers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewSpeciality = findViewById(R.id.recyclerViewSpeciality);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        loaderManager = LoaderManager.getInstance(this);

        adapter = new SpecialityAdapter();
        recyclerViewSpeciality.setAdapter(adapter);

        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        loaderManager.restartLoader(LOADER_ID, bundle, this);

        getData();

        adapter.setOnSpecialityClickListener(new SpecialityAdapter.OnSpecialityClickListener() {
            @Override
            public void onSpecialityClick(String speciality) {
                Intent intent = new Intent(MainActivity.this, WorkersBySpeciality.class);
                intent.putExtra("speciality", speciality);
                startActivity(intent);
            }
        });
    }


    private void getData() {
        LiveData<List<Worker>> workersFromDb = viewModel.getWorkers();
        workersFromDb.observe(this, new Observer<List<Worker>>() {
            @Override
            public void onChanged(List<Worker> workers) {
                // Удаляем worker с дубликатами специальностей и передаем новый workers в адаптер

                List<Worker> workersForAdapter = new ArrayList<>(workers);
                List<String> spec = new ArrayList<>();
                for (int i = 0; i < workersForAdapter.size(); i++) {
                    String str = workersForAdapter.get(i).getSpeciality();
                    if (spec.contains(str)) {
                        workersForAdapter.remove(i);
                        i--;
                    } else {
                        spec.add(str);
                    }
                }
                adapter.setWorkers(workersForAdapter);
            }
        });
    }

    @NonNull
    @Override
    public Loader<JSONObject> onCreateLoader(int id, @Nullable Bundle args) {
        JSONLoader jsonLoader = new JSONLoader(this, args);
        return jsonLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<JSONObject> loader, JSONObject data) {
        ArrayList<Worker> workers = JSONUtils.getWorkerFromJSON(data);
        if (workers != null && !workers.isEmpty()) {
            viewModel.deleteAllWorkers();
            adapter.clear();
            for (Worker worker : workers) {
                viewModel.insertWorker(worker);
            }
        }
        loaderManager.destroyLoader(LOADER_ID);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<JSONObject> loader) {

    }
}


















