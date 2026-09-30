package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
  @Query("SELECT * FROM notes ORDER BY timestamp DESC")
  fun getAllNotes(): Flow<List<NoteEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertNote(note: NoteEntity): Long

  @Query("DELETE FROM notes WHERE id = :id")
  suspend fun deleteNoteById(id: Long)
}

@Dao
interface ProgressDao {
  @Query("SELECT * FROM user_progress")
  fun getAllProgress(): Flow<List<ProgressEntity>>

  @Query("SELECT value FROM user_progress WHERE `key` = :key LIMIT 1")
  suspend fun getValue(key: String): String?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun setProgress(entity: ProgressEntity)
}
