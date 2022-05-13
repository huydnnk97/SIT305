package com.example.lostandfoundapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lostandfoundapp.model.Item;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private List<Item> itemList;
    private Context context;
    private OnRowClickListener listener;

    public ItemAdapter(List<Item> itemList, Context context, OnRowClickListener listener) {
        this.itemList = itemList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new ItemViewHolder(itemView,listener);

    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ItemViewHolder holder, int position) {
        holder.textView.setText(itemList.get(position).getLost()+":"+itemList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textView;
        public OnRowClickListener onRowClickListener;
        public ItemViewHolder(@NonNull View itemView,OnRowClickListener listener) {

            super(itemView);
            this.onRowClickListener=listener;
            itemView.setOnClickListener(this);
            textView=itemView.findViewById(R.id.item1);
        }

        @Override
        public void onClick(View view) {
            onRowClickListener.onItemClick(itemList.get(getAdapterPosition()).getId());
        }
    }
    public interface OnRowClickListener{
        void onItemClick(int id);
    }
}
