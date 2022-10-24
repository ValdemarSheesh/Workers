package com.example.workers.adapters;

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

public class SpecialityAdapter extends RecyclerView.Adapter<SpecialityAdapter.SpecialityViewHolder> {

    private List<Worker> workers;
    private OnSpecialityClickListener onSpecialityClickListener;

    public SpecialityAdapter() {
        workers = new ArrayList<>();
    }

    public interface OnSpecialityClickListener {
        void onSpecialityClick(String speciality);
    }

    @NonNull
    @Override
    public SpecialityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.speciality_item, parent, false);
        return new SpecialityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialityViewHolder holder, int position) {
        Worker worker = workers.get(position);
        holder.textViewSpeciality.setText(worker.getSpeciality());
    }

    @Override
    public int getItemCount() {
        return workers.size();
    }


    class SpecialityViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewSpeciality;

        public SpecialityViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewSpeciality = itemView.findViewById(R.id.textViewSpeciality);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onSpecialityClickListener != null) {
                        onSpecialityClickListener.onSpecialityClick(getWorkers().get(getAdapterPosition()).getSpeciality());
                    }
                }
            });
        }
    }

    public List<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(List<Worker> workers) {
        this.workers = workers;
        notifyDataSetChanged();
    }

    public void clear() {
        this.workers.clear();
        notifyDataSetChanged();
    }

    public void setOnSpecialityClickListener(OnSpecialityClickListener onSpecialityClickListener) {
        this.onSpecialityClickListener = onSpecialityClickListener;
    }
}

















