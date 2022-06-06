package com.example.tsansroom.room

import androidx.room.*

@Dao
interface BookDao {

    @Insert
    suspend fun addBook(book: Book)

    @Update
    suspend fun updateBook(book: Book)

    @Delete
    suspend fun deleteBook(book: Book)

    @Query ("SELECT * FROM book")
    suspend fun getBooks():List<Book>

    @Query("SELECT * FROM book WHERE id=:bookId")
    suspend fun getBook(bookId: Int):List<Book>
    abstract fun getBooks(bookId: Int): List<Book>
}