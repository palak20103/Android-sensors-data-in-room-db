package com.example.myassignment4;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "AccT")
public class AccT implements Serializable {
    @PrimaryKey
    @ColumnInfo(name = "timeStamp")
    public long timeStamp;
    @ColumnInfo(name = "X")
    public String X;
    @ColumnInfo(name = "Y")
    public String Y;
    @ColumnInfo(name = "Z")
    public String Z;
}