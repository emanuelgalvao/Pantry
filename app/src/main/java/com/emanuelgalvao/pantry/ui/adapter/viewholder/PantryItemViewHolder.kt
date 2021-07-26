package com.emanuelgalvao.pantry.ui.adapter.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.emanuelgalvao.pantry.R
import com.emanuelgalvao.pantry.service.listener.ItemListener
import com.emanuelgalvao.pantry.service.model.PantryItem
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class PantryItemViewHolder(itemView: View, val listener: ItemListener<PantryItem>) : RecyclerView.ViewHolder(itemView) {

    private var mFormatDate = SimpleDateFormat("dd/MM/yyyy")

    private var mLinearDueDate: LinearLayout = itemView.findViewById(R.id.linear_due_date)
    private var mTextDue: TextView = itemView.findViewById(R.id.text_due_date)
    private var mTextDate: TextView = itemView.findViewById(R.id.text_date)
    private var mTextItem: TextView = itemView.findViewById(R.id.text_item)
    private var mImageDelete: ImageView = itemView.findViewById(R.id.image_delete)

    fun bindData(item: PantryItem, showDeleteButton: Boolean) {
        mTextDate.text = item.dueDate.subSequence(0, 5)
        mTextItem.text = item.description

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        val currentDate = calendar.time
        val dueDate: Date = mFormatDate.parse(item.dueDate)

        val differenceDays = TimeUnit.MILLISECONDS.toDays(currentDate.time - dueDate.time)

        if (differenceDays > -2 && differenceDays < 0) {
            mLinearDueDate.background = ContextCompat.getDrawable(itemView.context, R.drawable.shape_rounded_due_expired_soon)
            mTextDue.setTextColor(ContextCompat.getColor(itemView.context, R.color.text_dark_yellow_soon))
            mTextDate.setTextColor(ContextCompat.getColor(itemView.context, R.color.text_dark_yellow_soon))
        } else if (differenceDays == 0L) {
            mLinearDueDate.background = ContextCompat.getDrawable(itemView.context, R.drawable.shape_rounded_due_today)
            mTextDue.setTextColor(ContextCompat.getColor(itemView.context, R.color.text_dark_yellow))
            mTextDate.setTextColor(ContextCompat.getColor(itemView.context, R.color.text_dark_yellow))
        } else if (differenceDays > 0) {
            mLinearDueDate.background = ContextCompat.getDrawable(itemView.context, R.drawable.shape_rounded_due_expired)
            mTextDue.setTextColor(ContextCompat.getColor(itemView.context, R.color.text_dark_red))
            mTextDate.setTextColor(ContextCompat.getColor(itemView.context, R.color.text_dark_red))
        }

        if (!showDeleteButton) {
            mImageDelete.isVisible = false
        } else {
            mImageDelete.setOnClickListener {
                listener.onDelete(item)
            }
        }
    }


}