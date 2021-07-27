package com.emanuelgalvao.pantry.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emanuelgalvao.pantry.R
import com.emanuelgalvao.pantry.service.listener.ItemListener
import com.emanuelgalvao.pantry.service.model.ShoppingItem
import com.emanuelgalvao.pantry.ui.adapter.ShoppingItemAdapter
import com.emanuelgalvao.pantry.util.AlertUtils
import com.emanuelgalvao.pantry.viewmodel.ShoppingListViewModel
import kotlinx.android.synthetic.main.fragment_shopping_list.*
import kotlinx.android.synthetic.main.fragment_shopping_list.view.*


class ShoppingListFragment : Fragment(), View.OnClickListener {

    private lateinit var mViewModel: ShoppingListViewModel
    private lateinit var mListener: ItemListener<ShoppingItem>
    private val mAdapter = ShoppingItemAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_shopping_list, container, false)

        mViewModel = ViewModelProvider(this).get(ShoppingListViewModel::class.java)

        val recycler = root.findViewById<RecyclerView>(R.id.recycler_shopping_list)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = mAdapter

        mListener = object: ItemListener<ShoppingItem> {
            override fun onDelete(item: ShoppingItem) {
                mViewModel.delete(item)
            }

            override fun onCheck(item: ShoppingItem) {
                mViewModel.update(item, true)
            }

            override fun onUnCheck(item: ShoppingItem) {
                mViewModel.update(item, false)
            }
        }

        root.image_help.setOnClickListener(this)

        observers()

        return root
    }

    override fun onResume() {
        super.onResume()
        mViewModel.list()
        mAdapter.attachListener(mListener)
    }

    private fun observers() {
        mViewModel.shoppingItemList.observe(viewLifecycleOwner, {
            if (it.count() >= 0) {
                mAdapter.updateList(it)
            }
        })

        mViewModel.validationDelete.observe(viewLifecycleOwner, {
            if (it.isSucess()) {
                AlertUtils.showSnackbar(root, "Item removido com sucesso!",
                    ContextCompat.getColor(requireContext(), R.color.snack_green))
            }
        })
    }

    private fun showHelpDialog() {
        AlertUtils.showInfoDialog(requireContext(), "Lista de compras", "Nesta tela você pode realizar o controle do que precisa comprar quando for ao mercado.\n\nVocê pode dar um 'check' nos itens que você já comprou ou excluir itens que não precisa mais comprar.")
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.image_help) {
            showHelpDialog()
        }
    }
}