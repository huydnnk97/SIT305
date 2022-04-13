package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NewAdapter.OnRowClickListener{

    TextView textView;
    Button btnLeft,btnRight;
    RecyclerView rv_Story;
    StoryAdapter storyAdapter;
    List<Story>storyList=new ArrayList<>();
    private static Integer[]storyImageList={R.drawable.image1,R.drawable.image2,R.drawable.image3,R.drawable.image4};
    int currentPosition=0;

    RecyclerView rv_New;
    NewAdapter newAdapter;
    List<New>newList=new ArrayList<>();
    private static Integer[]newImageList={R.drawable.new1,R.drawable.new2,R.drawable.new3,R.drawable.new4,R.drawable.new5,R.drawable.new6};
    private static String[]newNameList={"aaaaaaaaaa","bbbbbbbbb","ccccccccc","dddddddddd","eeeeeeeee","fffffffffff"};
    private static String[]newDetailList={"111111111111111111111111111111111111111111111111111111111111111111111","2222222222222222222222222222222222222222222222222222222222222222222","3333333333333333333333333333333333333333333333333333333333333333333333333333333","444444444444444444444444444444444444444444444444444444444","55555555555555555555555555555555555555555555555","6666666666666666666666666666666666666666666"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLeft=findViewById(R.id.btnLeft);
        btnRight=findViewById(R.id.btnRight);

        rv_Story=findViewById(R.id.rv_top_stories);
        storyAdapter=new StoryAdapter(storyList,MainActivity.this);
        RecyclerView.LayoutManager horizontal_manager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);

        rv_Story.setAdapter(storyAdapter);

        rv_Story.setLayoutManager(horizontal_manager);
//        rv_Story.setLayoutManager(gridLayoutManager);
        for(int i=0;i<storyImageList.length;i++){
            Story story=new Story(i,storyImageList[i]);
            storyList.add(story);
        }

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPosition<storyAdapter.getItemCount()){
                    currentPosition+=3;
                    if (currentPosition>storyAdapter.getItemCount()) currentPosition=storyAdapter.getItemCount()-1;
                    rv_Story.smoothScrollToPosition(currentPosition);
                }

            }
        });
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPosition>0){
                    currentPosition-=3;
                    if (currentPosition<0) currentPosition=0;
                    rv_Story.smoothScrollToPosition(currentPosition);
                }
            }
        });

        rv_New=findViewById(R.id.rv_new);
        newAdapter=new NewAdapter(newList,MainActivity.this,this);
        RecyclerView.LayoutManager vertical_manager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rv_New.setAdapter(newAdapter);
        rv_New.setLayoutManager(vertical_manager);
        for(int i=0;i<newImageList.length;i++){
            New new1=new New(i,newImageList[i],newNameList[i],newDetailList[i] );
            newList.add(new1);
        }
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);
        rv_New.setLayoutManager(gridLayoutManager);



    }

    @Override
    public void onItemClick(int image, String name, String detail) {
        NewFragment fragment=NewFragment.newInstance(image, name,detail);
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame,fragment).commit();
        findViewById(R.id.mainLayout).setVisibility(View.GONE);
        findViewById(R.id.abc).setVisibility(View.GONE);
        textView=findViewById(R.id.newTextView);
        textView.setText("RELATED STORY");
        rv_New.setLayoutManager(new GridLayoutManager(this,1));
        rv_New.setAdapter(newAdapter);

    }
}