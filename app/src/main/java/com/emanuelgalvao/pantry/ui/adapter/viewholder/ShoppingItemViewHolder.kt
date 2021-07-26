package com.emanuelgalvao.pantry.ui.adapter.viewholder

import android.graphics.Typeface
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.emanuelgalvao.pantry.R
import com.emanuelgalvao.pantry.service.listener.ItemListener
import com.emanuelgalvao.pantry.service.model.ShoppingItem

class ShoppingItemViewHolder(itemView: View, val listener: ItemListener<ShoppingItem>) : RecyclerView.ViewHolder(itemView) {

    private var mCheckBuyed: CheckBox = itemView.findViewById(R.id.check_buyed)
    private var mTextDescription: TextView = itemView.findViewById(R.id.text_description)
    private var mImageDelete: ImageView = itemView.findViewById(R.id.image_delete)

    fun bindData(item: ShoppingItem, showActions: Boolean) {

        mCheckBuyed.isChecked = item.buyed
        mTextDescription.text = "${item.quantity} - ${item.description}"

        if (item.buyed) {
            mTextDescription.setTextColor(ContextCompat.getColor(itemView.context, R.color.text_gray))
            mTextDescription.setTypeface(mCheckBuyed.typeface, Typeface.ITALIC)
        }

        if (!showActions) {
            mCheckBuyed.isVisible = false
            mImageDelete.isVisible = false
        } else {
            mImageDelete.setOnClickListener {
                listener.onDelete(item)
            }

            mCheckBuyed.setOnClickListener {
                if (!item.buyed) {
                    listener.onCheck(item)
                } else {
                    listener.onUnCheck(item)
                }
            }
        }
    }


}