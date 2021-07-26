package com.emanuelgalvao.pantry.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emanuelgalvao.pantry.R
import com.emanuelgalvao.pantry.service.listener.ItemListener
import com.emanuelgalvao.pantry.service.model.ShoppingItem
import com.emanuelgalvao.pantry.ui.adapter.viewholder.ShoppingItemViewHolder

class ShoppingItemAdapter: RecyclerView.Adapter<ShoppingItemViewHolder>() {

    private var mList: List<ShoppingItem> = arrayListOf()
    private lateinit var mListener: ItemListener<ShoppingItem>

    private var mShowActions: Boolean = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingItemViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.adapter_shopping_list_item, parent, false)
        return ShoppingItemViewHolder(item, mListener)
    }

    override fun onBindViewHolder(holder: ShoppingItemViewHolder, position: Int) {
        holder.bindData(mList[position], mShowActions)
    }

    override fun getItemCount(): Int {
        return mList.count()
    }

    fun attachListener(listener: ItemListener<ShoppingItem>) {
        mListener = listener
    }

    fun setShowActions(showActions: Boolean) {
        mShowActions = showActions
    }

    fun updateList(list: List<ShoppingItem>) {
        mList = list
        notifyDataSetChanged()
    }
}