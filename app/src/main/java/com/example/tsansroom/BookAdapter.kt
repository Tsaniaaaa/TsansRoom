package com.example.tsansroom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tsansroom.room.Book
import kotlinx.android.synthetic.main.list_book.view.*

class BookAdapter(private val books: ArrayList<Book>, private val listener: onAdapterListener) :
    RecyclerView.Adapter<BookAdapter.BookViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return BookViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_book, parent, false)
        )
    }

    override fun getItemCount() = books.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book  = books[position]
        holder.view.text_title.text = book.title
        holder.view.text_title.setOnClickListener{
            listener.onClick(book)
        }
        holder.view.icon_edit.setOnClickListener{
            listener.onUpdate(book)
        }
        holder.view.icon_delete.setOnClickListener{
            listener.onDelete(book)
        }
    }

    class BookViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(list: List<Book>){
        books.clear()
        books.addAll(list)
        notifyDataSetChanged()
    }

    interface onAdapterListener{
        fun onClick(book: Book)
        fun onUpdate(book: Book)
        fun onDelete(book: Book)
    }
}