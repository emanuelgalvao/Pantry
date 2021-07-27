package com.emanuelgalvao.pantry.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emanuelgalvao.pantry.R
import com.emanuelgalvao.pantry.service.listener.ItemListener
import com.emanuelgalvao.pantry.service.model.PantryItem
import com.emanuelgalvao.pantry.ui.adapter.viewholder.PantryItemViewHolder

class PantryItemAdapter: RecyclerView.Adapter<PantryItemViewHolder>() {

    private var mList: List<PantryItem> = arrayListOf()
    private lateinit var mListener: ItemListener<PantryItem>
    private var mShowDeleteButton: Boolean = true
    private var mDueDays: Int = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PantryItemViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.adapter_pantry_item, parent, false)
        return PantryItemViewHolder(item, mListener)
    }

    override fun onBindViewHolder(holder: PantryItemViewHolder, position: Int) {
        holder.bindData(mList[position], mShowDeleteButton, mDueDays)
    }

    override fun getItemCount(): Int {
        return mList.count()
    }

    fun attachListener(listener: ItemListener<PantryItem>) {
        mListener = listener
    }

    fun setShowDeleteButton(showDeleteButton: Boolean) {
        mShowDeleteButton = showDeleteButton
    }

    fun setDueDays(dueDays: Int) {
        mDueDays = dueDays
    }

    fun updateList(list: List<PantryItem>) {
        mList = list
        notifyDataSetChanged()
    }
}