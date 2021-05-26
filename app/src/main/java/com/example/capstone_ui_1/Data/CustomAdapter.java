package com.example.capstone_ui_1.Data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.capstone_ui_1.R;

import java.util.ArrayList;
import java.util.Collection;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CumstomViewHolder> implements Filterable{

    ArrayList<ChosunDTO> arrayList;
    ArrayList<ChosunDTO> arrayListFull;

    private Context context;
    private Toast toast;
    com.example.capstone_ui_1.Data.OnClassItemClickListener listener;
    private Button layout_legister;

    private com.example.capstone_ui_1.Data.RecyclerViewClickInterface recyclerViewClickInterface;

    public CustomAdapter(ArrayList<ChosunDTO> arrayList, Context context, com.example.capstone_ui_1.Data.RecyclerViewClickInterface recyclerViewClickInterface) {
        this.arrayList = arrayList;
        this.context = context;
        arrayListFull = new ArrayList<>(arrayList);
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @NonNull
    @Override
    public CumstomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        CumstomViewHolder holder = new CumstomViewHolder(view, this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CumstomViewHolder holder, int position) {
        holder.tv_building.setText(arrayList.get(position).getBuilding());
        holder.tv_major.setText(arrayList.get(position).getMajor());
        holder.tv_professor.setText(arrayList.get(position).getProfessor());
        holder.tv_latitude.setText(Double.toString(arrayList.get(position).getLatitude()));
        holder.tv_longtitude.setText(Double.toString(arrayList.get(position).getLongtitude()));
    }

    @Override
    public int getItemCount() {
        return (arrayList !=null ? arrayList.size() : 0);
    }


    @Override
    public Filter getFilter() {
        return FilterUser;
    }

    public void setOnClickListener(com.example.capstone_ui_1.Data.OnClassItemClickListener listener) {
        this.listener =  listener;
    }


    public void onItemClick(CumstomViewHolder holder, View view, int position) {
        if(listener != null) {
            listener.onItemClick(holder,view,position); }
    }

    private Filter FilterUser = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String searchText = charSequence.toString().toLowerCase();
            ArrayList<ChosunDTO>tempList=new ArrayList<>();
            if (searchText.length() == 0 || searchText.isEmpty()) {
                tempList.addAll(arrayListFull);
            } else {
                for (ChosunDTO item : arrayListFull) {
                    if (item.getBuilding().contains(searchText) || (item.getMajor().contains(searchText) || (item.getProfessor().contains((searchText))))) {
                        tempList.add(item);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = tempList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            arrayList.clear();
            arrayList.addAll((Collection<? extends ChosunDTO>) results.values);
            notifyDataSetChanged();
        }
    };


    public class CumstomViewHolder extends RecyclerView.ViewHolder {
        TextView tv_building;
        TextView tv_professor;
        TextView tv_major;
        TextView tv_latitude;
        TextView tv_longtitude;
        Button layout_legister;

        public CumstomViewHolder(@NonNull View itemView,CustomAdapter listener) {
            super(itemView);
            this.tv_building=itemView.findViewById(R.id.tv_building);
            this.tv_professor=itemView.findViewById(R.id.tv_professor);
            this.tv_major =itemView.findViewById(R.id.tv_major);
            this.tv_latitude=itemView.findViewById(R.id.tv_latitude);
            this.tv_longtitude=itemView.findViewById(R.id.tv_longtitude);
            this.layout_legister = itemView.findViewById(R.id.layout_legister);

            layout_legister.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    recyclerViewClickInterface.onItemClick(getAdapterPosition());
//                    int position = getAdapterPosition();
//
//
//                    if(listener != null){
//                        listener.onItemClick(CumstomViewHolder.this, v, position);
//                    }
                }
            });
            itemView.setOnLongClickListener((v) -> {
                recyclerViewClickInterface.onLongItemClick(getAdapterPosition());

                return true;
            });
        }
    }
}
