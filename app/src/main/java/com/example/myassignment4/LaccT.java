package com.example.myassignment4;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "LaccT")
public class LaccT implements Serializable {
    @PrimaryKey
    @ColumnInfo(name = "timeStamp")
    public long timeStamp;
    @ColumnInfo(name = "lX")
    public String lX;
    @ColumnInfo(name = "lY")
    public String lY;
    @ColumnInfo(name = "lZ")
    public String lZ;
}