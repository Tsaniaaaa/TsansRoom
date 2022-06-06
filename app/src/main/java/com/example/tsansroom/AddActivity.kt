package com.example.tsansroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tsansroom.room.Book
import com.example.tsansroom.room.BookDb
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddActivity : AppCompatActivity() {
    val db by lazy { BookDb(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setupListener()
    }

    fun setupListener(){
        btn_save.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.bookDao().addBook(
                    Book(0, et_title.text.toString(),
                    et_synopsys.text.toString())
                )

                finish()
            }
        }
    }
}