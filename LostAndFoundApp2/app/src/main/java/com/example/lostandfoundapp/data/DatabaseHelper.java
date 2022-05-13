package com.example.lostandfoundapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.lostandfoundapp.Util.Util;
import com.example.lostandfoundapp.model.Item;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper  extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_USER_TABLE = "CREATE TABLE " + Util.TABLE_NAME + "(" + Util.ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                + Util.LOST + " TEXT," +Util.NAME+ " TEXT," +Util.PHONE+ " TEXT," +Util.DESCRIPTION+ " TEXT," +Util.DATE+ " TEXT," +Util.LOCATION+ " TEXT"+ ")";
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Util.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
    public long insertItem(Item item){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        Log.d("abc check", "insertItem: ");
        contentValues.put(Util.LOST,item.getLost());
        contentValues.put(Util.NAME,item.getName());
        contentValues.put(Util.PHONE,item.getPhone());
        contentValues.put(Util.DESCRIPTION,item.getDescription());
        contentValues.put(Util.DATE,item.getDate());
        contentValues.put(Util.LOCATION,item.getLocation());
        long newRowId=db.insert(Util.TABLE_NAME,null,contentValues);
//        db.close();

        return newRowId;
    }
    public List<Item>fetchAllItem(){
        List<Item>itemList=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(" SELECT * FROM " + Util.TABLE_NAME,null);
        if(cursor.moveToFirst()){
            do{
                Item item=new Item();
                item.setId(cursor.getInt(0));
                item.setLost(cursor.getString(1));
                item.setName(cursor.getString(2));
                item.setPhone(cursor.getString(3));
                item.setDescription(cursor.getString(4));
                item.setDate(cursor.getString(5));
                item.setLocation(cursor.getString(6));
                itemList.add(item);
            }
            while (cursor.moveToNext());
        }
        return itemList;
    }
    public Item fetchItem(int id){
        Item item=new Item();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(" SELECT * FROM " + Util.TABLE_NAME,null);
        if(cursor.moveToFirst()){
            do{
                if(cursor.getInt(0)==id){
                    item.setId(id);
                    item.setLost(cursor.getString(1));
                    item.setName(cursor.getString(2));
                    item.setPhone(cursor.getString(3));
                    item.setDescription(cursor.getString(4));
                    item.setDate(cursor.getString(5));
                    item.setLocation(cursor.getString(6));
                    return item;
                }
            }while (cursor.moveToNext());
        }
        return item;
    }
    public void deleteItem(int id){
        SQLiteDatabase db=this.getReadableDatabase();
        db.delete(Util.TABLE_NAME,Util.ITEM_ID+"="+id,null);
    }

}
