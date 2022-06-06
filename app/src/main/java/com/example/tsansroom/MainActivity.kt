package com.example.tsansroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tsansroom.room.Book
import com.example.tsansroom.room.BookDb
import com.example.tsansroom.room.Constant
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    val db by lazy { BookDb (this) }
    lateinit var bookAdapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()

    }

    override fun onStart() {
        super.onStart()
        loadBook()

    }

    fun loadBook(){
        CoroutineScope(Dispatchers.IO).launch{
            val books = db.bookDao().getBooks()
            Log.d("MainActivity", "dbResponse: $books")
            withContext(Dispatchers.Main) {
                bookAdapter.setData(books)
            }
        }
    }

    fun intentEdit(bookId: Int, intentType: Int){
        startActivity(
            Intent(applicationContext, EditActivity::class.java)
                .putExtra("intent_id", bookId)
                .putExtra("intent_type", intentType)
        )

    }

    private fun setupRecyclerView() {
        bookAdapter = BookAdapter(arrayListOf(), object : BookAdapter.onAdapterListener{
            override fun onClick(book: Book) {
                //membaca detail movie
                intentEdit(book.id, Constant.TYPE_READ)
            }

            override fun onUpdate(book: Book) {
                intentEdit(book.id, Constant.TYPE_UPDATE)
            }

            override fun onDelete(book: Book) {
                deleteDialog(book)
            }

        })

        rv_book.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = bookAdapter
        }
    }

    private fun deleteDialog(book: Book) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Confirmation")
            setMessage("Want to delete this? ${book.title}?")
            setNegativeButton("Cancel") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Delete") { dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch{
                    db.bookDao().deleteBook(book)
                    loadBook()
                }
            }
        }

        alertDialog.show()
    }
}