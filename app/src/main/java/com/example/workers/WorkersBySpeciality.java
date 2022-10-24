package com.example.workers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workers.adapters.WorkersBySpecialityAdapter;
import com.example.workers.data.MainViewModel;
import com.example.workers.data.Worker;

import java.util.ArrayList;
import java.util.List;

public class WorkersBySpeciality extends AppCompatActivity {

    private RecyclerView recyclerViewWorkersBySpeciality;
    private TextView textViewChosenSpeciality;
    private String speciality;
    private MainViewModel viewModel;
    private WorkersBySpecialityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workers_by_speciality);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("speciality"))
            speciality = intent.getStringExtra("speciality");
        else
            finish();

        recyclerViewWorkersBySpeciality = findViewById(R.id.recyclerViewWorkersBySpeciality);
        textViewChosenSpeciality = findViewById(R.id.textViewChosenSpeciality);
        textViewChosenSpeciality.setText(speciality);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        adapter = new WorkersBySpecialityAdapter();
        recyclerViewWorkersBySpeciality.setAdapter(adapter);

        getData();

        adapter.setOnInfoClickListener(new WorkersBySpecialityAdapter.OnInfoClickListener() {
            @Override
            public void OnInfoClick(Worker worker) {
                Intent intent1 = new Intent(WorkersBySpeciality.this, DetailInfoOfWorkers.class);
                intent1.putExtra(Worker.class.getSimpleName(), worker);
                startActivity(intent1);
            }
        });
    }

    private void getData() {
        LiveData<List<Worker>> workersFromDB = viewModel.getWorkersBySpeciality(speciality);
        workersFromDB.observe(this, new Observer<List<Worker>>() {
            @Override
            public void onChanged(List<Worker> workers) {
                adapter.setWorkers(workers);
            }
        });
    }
}












