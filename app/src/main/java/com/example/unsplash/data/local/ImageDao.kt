package com.example.unsplash.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.unsplash.data.models.ImageData

@Dao
interface ImageDao {

    // Inserts a list of ImageData objects into the database. If there is a conflict
    // with existing data, it replaces any conflicting rows.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImages(imageList: List<ImageData>)

    // Retrieves a list of ImageData objects from the database based on a given page number.
    // Uses a SQL query to search for all rows in the 'imagedata' table where the 'pageNum'
    // column matches the given 'page' parameter. Returns a list of matching ImageData objects.
    @Query("SELECT * FROM imagedata WHERE pageNum=:page")
    suspend fun fetchImagesForPage(page: Int): List<ImageData>
}