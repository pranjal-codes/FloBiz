package com.example.flobiz.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flobiz.R
import com.example.flobiz.models.Item
import java.text.SimpleDateFormat
import java.util.*


class QuestionListAdapter(
    private val dataSet: List<Item>,
    private val itemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    // define the type
    // of item
    companion object {
        const val BANNER_TYPE = 1
        const val CONTENT_TYPE = 0
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // if content type, inflate the item_question layout
        //else banner layout
        return when (viewType) {
            CONTENT_TYPE -> {
                val layoutOne: View = LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.item_question, parent,
                        false
                    )
                ItemViewHolder(layoutOne)
            }
            else -> {
                val layoutTwo: View = LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.item_banner, parent,
                        false
                    )
                BannerViewHolder(layoutTwo)
            }
        }
    }


    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        when (holder.itemViewType) {
            CONTENT_TYPE -> {
                val item: Item = dataSet[position]
                (holder as ItemViewHolder).setView(item, itemClickListener)
            }
            BANNER_TYPE -> {
            }
            else -> return
        }
    }


    override fun getItemCount() = dataSet.size

    override fun getItemViewType(position: Int): Int {
        if (position % 7 == 0 && position != 0)
            return BANNER_TYPE;
        return CONTENT_TYPE;
    }

    fun refreshRecyclerView() {
        notifyDataSetChanged()
    }


    // Create classes for each layout ViewHolder.
    internal class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textOwnerDisplayName: TextView =
            itemView.findViewById(R.id.text_owner_display_name)
        private val textOwnerQuestion: TextView = itemView.findViewById(R.id.text_owner_question)
        private val textDatePosted: TextView = itemView.findViewById(R.id.text_date_posted)
        private val imageOwnerProfilePic: ImageView =
            itemView.findViewById(R.id.image_owner_profile_pic)
        private var imageUrl: String? = null


        fun setView(item: Item, clickListener: OnItemClickListener) {
            textOwnerDisplayName.text = item.owner.display_name
            textOwnerQuestion.text = item.title
            textDatePosted.text =
                "Posted On : " + getDate(item.creation_date.toLong(), "dd-MM-yyy")

            imageUrl = item.owner.profile_image
            Glide.with(itemView)
                .load(imageUrl)
                .thumbnail(0.2f)
                .circleCrop()
                .into(imageOwnerProfilePic)

            itemView.setOnClickListener {
                clickListener.onClick(item)
            }

        }

        /*
        * Returns date from given milliseconds
        * */
        private fun getDate(milliSeconds: Long, dateFormat: String?): String? {
            val formatter = SimpleDateFormat(dateFormat)
            val calendar: Calendar = Calendar.getInstance()
            calendar.timeInMillis = (milliSeconds * 1000)
            return formatter.format(calendar.time)
        }
    }


    internal class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}

interface OnItemClickListener {
    fun onClick(item: Item)
}