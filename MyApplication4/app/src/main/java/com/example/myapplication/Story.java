package com.example.myapplication;

public class Story {
    private int id,image;
    public Story(int id,int image){
        this.id=id;
        this.image=image;
    }
    public int getId(){return id;}
    public void setId(int id){this.id=id;}
    public int getImage(){return image;}
}
