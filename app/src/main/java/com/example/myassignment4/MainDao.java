package com.example.myassignment4;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MainDao {

    @Insert
    void insertAccT(AccT table);
    @Insert
    void insertLaccT(LaccT table);
    @Insert
    void insertTT(TT table);
    @Insert
    void insertGPST(GPST table);
    @Insert
    void insertPT(PT table);
    @Insert
    void insertLT(LT table);


    @Query("SELECT * FROM AccT where timeStamp>:one")
    List<AccT> getAccT(long one);
    @Query("SELECT * FROM TT where timeStamp>:one")
    List<TT> getTT(long one);

}
