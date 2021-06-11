package com.example.myassignment4;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "GPST")
public class GPST implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int ID;

    @ColumnInfo(name = "lat")
    public String lat;
    @ColumnInfo(name = "lng")
    public String lng;
}