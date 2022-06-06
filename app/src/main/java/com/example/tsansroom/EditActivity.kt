package com.example.tsansroom

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.tsansroom.room.Book
import com.example.tsansroom.room.BookDb
import com.example.tsansroom.room.Constant
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {

    val db by lazy { BookDb(this) }
    private var bookId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        setupView()
        setupListener()

    }

    fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when (intentType) {
            Constant.TYPE_CREATE -> {
                button_update.visibility = View.GONE

            }
            Constant.TYPE_READ -> {
                button_save.visibility = View.GONE
                button_update.visibility = View.GONE
                getBook()
            }
            Constant.TYPE_UPDATE -> {
                button_save.visibility = View.GONE
                getBook()
            }
        }
    }

    fun setupListener(){
        button_save.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch{
                db.bookDao().addBook(
                    Book (0, edit_title.text.toString(), edit_book.text.toString() )
                )
                finish()
            }
        }
        button_update.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch{
                db.bookDao().updateBook(
                    Book (bookId, edit_title.text.toString(), edit_book.text.toString() )
                )
                finish()
            }
        }

    }

    fun getBook() {
        bookId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch{
            val books = db.bookDao().getBooks(bookId)[0]
            edit_title.setText(books.title)
            edit_book.setText(books.desc)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}