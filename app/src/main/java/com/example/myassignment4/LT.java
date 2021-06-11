package com.example.myassignment4;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity(tableName = "LT")
public class LT implements Serializable {
    @PrimaryKey
    @ColumnInfo(name = "timeStamp")
    public long timeStamp;
    @ColumnInfo(name = "LightValue")
    public String LightValue;
}