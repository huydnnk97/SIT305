package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.StoryViewHolder> {
    private List<Story> storyList;
    private Context context;
    public StoryAdapter(List<Story>storyList,Context context){
        this.storyList=storyList;
        this.context=context;
    }
    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_story,parent,false);
        return new StoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryAdapter.StoryViewHolder holder, int position) {
        holder.iv_top_story.setImageResource(storyList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return storyList.size();
    }

    public class StoryViewHolder extends RecyclerView.ViewHolder{
        public ImageView iv_top_story;
        public StoryViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_top_story=itemView.findViewById(R.id.iv_top_story);
        }
    }
}
