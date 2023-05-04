package com.example.unsplash.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.unsplash.data.models.ImageData

// Defines a database class for ImageData objects using the Room persistence library.
@Database(entities = [ImageData::class], version = 1, exportSchema = false)
abstract class ImageDatabase : RoomDatabase() {

    // Returns a DAO (Data Access Object) for ImageData objects.
    // This is an abstract method that is implemented by Room at compile time.
    abstract fun getDao(): ImageDao
}
