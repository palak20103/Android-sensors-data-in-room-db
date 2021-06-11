package com.example.myassignment4;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "PT")
public class PT implements Serializable {
    @PrimaryKey
    @ColumnInfo(name = "timeStamp")
    public long timeStamp;
    @ColumnInfo(name = "ProximityValue")
    public String ProximityValue;
}