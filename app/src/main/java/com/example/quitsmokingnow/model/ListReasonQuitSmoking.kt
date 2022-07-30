package com.example.quitsmokingnow.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "table_reason")
data class ListReasonQuitSmoking(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "reason") val mReason: String

) {
    override fun toString(): String {
        return "ListReasonQuitSmoking(id=$id, mReason='$mReason')"
    }
}