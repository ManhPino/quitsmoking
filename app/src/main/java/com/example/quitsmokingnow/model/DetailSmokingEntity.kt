package com.example.quitsmokingnow.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "table_detail_smoking")
data class DetailSmokingEntity(
    @PrimaryKey val u_id: Int?,
    @ColumnInfo(name = "user_smoking") val username: String?,
    @ColumnInfo(name = "amount_per_day") var amount: String,
    @ColumnInfo(name = "price_per_cigarette") var price: String,
    @ColumnInfo(name = "time_quitSmoking") var time: String

) {
    override fun toString(): String {
        return "DetailSmokingEntity(u_id=$u_id, username=$username, amount='$amount', price='$price', time='$time')"
    }
}