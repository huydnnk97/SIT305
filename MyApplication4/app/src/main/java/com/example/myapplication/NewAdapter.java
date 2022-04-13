package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NewAdapter extends RecyclerView.Adapter<NewAdapter.NewViewHolder> {
    private List<New> newList;
    private Context context;
    private OnRowClickListener listener;
    public boolean isChosen=false;
    public NewAdapter(List<New> newList,Context context,OnRowClickListener listener){
        this.newList=newList;
        this.context=context;
        this.listener=listener;
    }
    @NonNull
    @Override
    public NewAdapter.NewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        if (!isChosen){
        itemView=LayoutInflater.from(parent.getContext()).inflate(R.layout.vertical_new,parent,false);}
        else{
            itemView=LayoutInflater.from(parent.getContext()).inflate(R.layout.vertical_new_s,parent,false);}
        return new NewViewHolder(itemView,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull NewAdapter.NewViewHolder holder, int position) {
        holder.image_new.setImageResource(newList.get(position).getImage());
        holder.tv_name.setText(newList.get(position).getName());
        holder.tv_detail.setText(newList.get(position).getDetail());
    }

    @Override
    public int getItemCount() {
        return newList.size();
    }
    public class NewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView image_new;
        public TextView tv_detail,tv_name;
        public OnRowClickListener onRowClickListener;
        public NewViewHolder(@NonNull View itemView,OnRowClickListener onRowClickListener) {
            super(itemView);
            image_new=itemView.findViewById(R.id.image_new);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_detail=itemView.findViewById(R.id.tv_detail);

            this.onRowClickListener=onRowClickListener;
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v){
            onRowClickListener.onItemClick(newList.get(getAdapterPosition()).getImage(),newList.get(getAdapterPosition()).getName(),newList.get(getAdapterPosition()).getDetail());
            isChosen=true;
        }
    }
    public interface OnRowClickListener{
        void onItemClick(int image, String name, String detail);
    }
}
