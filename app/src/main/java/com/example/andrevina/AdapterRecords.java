package com.example.andrevina;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdapterRecords extends RecyclerView.Adapter<AdapterRecords.ViewHolderRecords> {

    List<Record> records;

    public AdapterRecords(List<Record> records) {
        this.records = records;
    }

    @NonNull
    @Override
    public ViewHolderRecords onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, null, false);
        return new ViewHolderRecords(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderRecords viewHolderRecords, int i) {
        Record record = records.get(i);
        viewHolderRecords.attempts.setText(Integer.toString(record.intentos));
        viewHolderRecords.name.setText(records.get(i).nombre);
        viewHolderRecords.imageView.setImageURI(records.get(i).image);
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public class ViewHolderRecords extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name;
        TextView attempts;
        public ViewHolderRecords(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            attempts = itemView.findViewById(R.id.attempts);
        }
    }
}
