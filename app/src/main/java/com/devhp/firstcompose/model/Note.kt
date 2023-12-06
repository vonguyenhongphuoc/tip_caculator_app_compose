package com.devhp.firstcompose.model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.util.UUID
@RequiresApi(Build.VERSION_CODES.O)
data class Note constructor(
    val iD: UUID = UUID.randomUUID(),
    val title: String, val description: String, val entryData: LocalDateTime = LocalDateTime.now()
)