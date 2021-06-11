package com.example.myassignment4;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {AccT.class,LaccT.class,TT.class,GPST.class,LT.class,PT.class},version = 4,exportSchema = false)
public abstract class myDB extends RoomDatabase {

    private static myDB database;

    private static String DATABASE_NAME = "database";

    public synchronized static myDB getInstance(Context context){
        if(database == null){
            database = Room.databaseBuilder(context.getApplicationContext(),myDB.class,DATABASE_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return database;
    }
    public abstract MainDao mainDao();
}
