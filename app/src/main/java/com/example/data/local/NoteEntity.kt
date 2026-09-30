package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(
  @PrimaryKey(autoGenerate = true) val id: Long = 0,
  val title: String,
  val content: String,
  val topicTag: String,
  val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "user_progress")
data class ProgressEntity(
  @PrimaryKey val key: String,
  val value: String
)
