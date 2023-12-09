package com.devhp.firstcompose.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity(tableName = "note_tbl")
data class Note constructor(
    @PrimaryKey
    val iD: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "note_title")
    val title: String,
    @ColumnInfo(name = "note_description")
    val description: String,
    @ColumnInfo(name = "note_entry_date")
//    @field:TypeConverters(DateConverter::class)
    val entryDate: Date = Date()

)




