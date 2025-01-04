package com.example.myapplicationrv.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationrv.R;
import com.example.myapplicationrv.models.Data;

import java.util.ArrayList;

public class CustomeAdapter extends RecyclerView.Adapter<CustomeAdapter.myViewHolder> implements android.widget.Filterable {

    private ArrayList<Data> arr;
    private ArrayList<Data> filteredArr;
    private OnItemClickListener listener;

    public CustomeAdapter(ArrayList<Data> arr, OnItemClickListener listener) {
        this.arr = arr;
        this.filteredArr = new ArrayList<>(arr);
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Data data);
    }

    // ViewHolder
    public class myViewHolder extends RecyclerView.ViewHolder {

        TextView username;
        TextView nameVersion;
        ImageView imageViewItem;

        public myViewHolder(View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.textName);
            nameVersion = itemView.findViewById(R.id.textVer);
            imageViewItem = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(filteredArr.get(position));
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public CustomeAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomeAdapter.myViewHolder holder, int position) {
        holder.username.setText(filteredArr.get(position).getName());
        holder.nameVersion.setText(filteredArr.get(position).getVersion());
        holder.imageViewItem.setImageResource(filteredArr.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return filteredArr.size();
    }

    @Override
    public android.widget.Filter getFilter() {
        return new android.widget.Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String query = constraint.toString().toLowerCase();
                ArrayList<Data> filtered = new ArrayList<>();
                if (query.isEmpty()) {
                    filtered = arr;
                } else {
                    for (Data data : arr) {
                        if (data.getName().toLowerCase().contains(query)) {
                            filtered.add(data);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filtered;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredArr = (ArrayList<Data>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
