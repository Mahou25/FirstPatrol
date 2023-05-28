package com.example.firstpatrol;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class InspectionAdapter extends RecyclerView.Adapter<InspectionAdapter.InspectionViewHolder> {
    private List<DossierInspection> inspectionList;
    private Context context;

    public InspectionAdapter(List<DossierInspection> inspectionList, Context context) {
        this.inspectionList = inspectionList;
        this.context = context;
    }

    @NonNull
    @Override
    public InspectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_inspection, parent, false);
        return new InspectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InspectionViewHolder holder, int position) {
        DossierInspection inspection = inspectionList.get(position);

        holder.textViewName.setText(inspection.getName());
        holder.textViewDate.setText(inspection.getDate());
    }

    @Override
    public int getItemCount() {
        return inspectionList.size();
    }

    public class InspectionViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewDate;

        public InspectionViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_name);
            textViewDate = itemView.findViewById(R.id.text_date);
        }
    }
}

