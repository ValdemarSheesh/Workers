package com.example.workers.adapters;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workers.R;
import com.example.workers.data.Worker;

import java.util.ArrayList;
import java.util.List;

public class WorkersBySpecialityAdapter extends RecyclerView.Adapter<WorkersBySpecialityAdapter.WorkersBySpecialityViewHolder> {

    private List<Worker> workers;
    private OnInfoClickListener onInfoClickListener;

    public WorkersBySpecialityAdapter() {
        this.workers = new ArrayList<>();
    }

    public interface OnInfoClickListener {
        void OnInfoClick(Worker worker);
    }

    @NonNull
    @Override
    public WorkersBySpecialityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.worker_info_item, parent, false);
        return new WorkersBySpecialityViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull WorkersBySpecialityViewHolder holder, int position) {
        Worker worker = workers.get(position);
        holder.textViewName.setText(worker.getName());
        holder.textViewSurname.setText(worker.getSurname());
        holder.textViewAge.setText(String.format(holder.itemView.getResources().getString(R.string.text_age), worker.getBirthday()));
    }

    @Override
    public int getItemCount() {
        return workers.size();
    }

    class WorkersBySpecialityViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private TextView textViewSurname;
        private TextView textViewAge;

        public WorkersBySpecialityViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewSurname = itemView.findViewById(R.id.textViewSurname);
            textViewAge = itemView.findViewById(R.id.textViewAge);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onInfoClickListener != null) {
                        onInfoClickListener.OnInfoClick(workers.get(getAdapterPosition()));
                    }
                }
            });
        }
    }

    public void setWorkers(List<Worker> workers) {
        this.workers = workers;
    }

    public void clear() {
        this.workers.clear();
        notifyDataSetChanged();
    }

    public void setOnInfoClickListener(OnInfoClickListener onInfoClickListener) {
        this.onInfoClickListener = onInfoClickListener;
    }
}















