package com.example.myapplication;

public class New {
    private int id,image;
    private String name,detail;
    public New(int id,int image, String name, String detail){
        this.id=id;
        this.image=image;
        this.name=name;
        this.detail=detail;
    }
    public int getId(){return this.id;}
    public void setId(int id){this.id=id;}
    public int getImage(){return this.image;}
    public void setImage(int image){this.image=image;}
    public String getName(){return this.name;}
    public void setName(String name){this.name=name;}
    public String getDetail(){return this.detail;}
    public  void setDetail(String detail){this.detail=detail;}
}
