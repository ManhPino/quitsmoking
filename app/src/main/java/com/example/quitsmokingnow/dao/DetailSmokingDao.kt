package com.example.quitsmokingnow.dao

import androidx.room.*
import com.example.quitsmokingnow.model.DetailSmokingEntity
import com.example.quitsmokingnow.model.ListReasonQuitSmoking
@Dao
public interface DetailSmokingDao {
    @Query("SELECT * FROM table_detail_smoking")
    fun getAll(): List<DetailSmokingEntity>

    @Insert
    fun insertDetail(vararg detailSmokingEntity: DetailSmokingEntity)

    @Delete
    fun delete(detailSmokingEntity: DetailSmokingEntity)

    @Query("DELETE from table_detail_smoking")
    fun deleteAllDetail();

    @Update()
    fun update(detail:DetailSmokingEntity);
}