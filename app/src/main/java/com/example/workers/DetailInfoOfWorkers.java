package com.example.workers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.workers.data.MainViewModel;
import com.example.workers.data.Worker;

import java.util.ArrayList;
import java.util.List;

public class DetailInfoOfWorkers extends AppCompatActivity {

    private TextView textViewName;
    private TextView textViewSurname;
    private TextView textViewBirthday;
    private TextView textViewAge;
    private TextView textViewSpeciality;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info_of_workers);
        textViewName = findViewById(R.id.textViewName);
        textViewSurname = findViewById(R.id.textViewSurname);
        textViewBirthday = findViewById(R.id.textViewBirthday);
        textViewAge = findViewById(R.id.textViewAge);
        textViewSpeciality = findViewById(R.id.textViewSpeciality);

        Bundle bundle = getIntent().getExtras();

        Worker worker;
        if (bundle != null) {
            worker = bundle.getParcelable(Worker.class.getSimpleName());
            textViewName.setText(String.format(getString(R.string.text_name), worker.getName()));
            textViewSurname.setText(String.format(getString(R.string.text_surname), worker.getSurname()));
            textViewBirthday.setText(String.format(getString(R.string.text_birthday), worker.getBirthday()));
            textViewAge.setText(String.format(getString(R.string.text_age), worker.getAge()));
            textViewSpeciality.setText(String.format(getString(R.string.text_speciality), worker.getSpeciality()));
        } else
            finish();
    }
}