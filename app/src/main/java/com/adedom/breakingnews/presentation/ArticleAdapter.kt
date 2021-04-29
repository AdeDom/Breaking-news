package com.adedom.breakingnews.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.adedom.breakingnews.R
import com.adedom.breakingnews.data.db.entities.ArticleDb
import com.adedom.breakingnews.utils.load
import kotlinx.android.synthetic.main.item_main.view.*

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    private var listener: ((ArticleDb) -> Unit)? = null

    private val diffUtil = object : DiffUtil.ItemCallback<ArticleDb>() {
        override fun areItemsTheSame(oldItem: ArticleDb, newItem: ArticleDb): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ArticleDb, newItem: ArticleDb): Boolean {
            return oldItem == newItem
        }
    }

    private val asyncListDiffer = AsyncListDiffer(this, diffUtil)

    private val list: MutableList<ArticleDb>
        get() = asyncListDiffer.currentList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_main, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.itemView.apply {
            val item = list[position]

            tvTitle.text = item.title
            tvDescription.text = item.description
            ivImage.load(item.urlToImage)

            setOnClickListener {
                listener?.invoke(item)
            }
        }
    }

    override fun getItemCount(): Int = list.size

    fun submitList(list: List<ArticleDb>) = asyncListDiffer.submitList(list)

    fun setOnClickListener(listener: (ArticleDb) -> Unit) {
        this.listener = listener
    }

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
