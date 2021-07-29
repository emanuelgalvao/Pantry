package com.emanuelgalvao.pantry.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.emanuelgalvao.pantry.R
import com.emanuelgalvao.pantry.databinding.FragmentShoppingListBinding
import com.emanuelgalvao.pantry.service.listener.ItemListener
import com.emanuelgalvao.pantry.service.model.ShoppingItem
import com.emanuelgalvao.pantry.ui.adapter.ShoppingItemAdapter
import com.emanuelgalvao.pantry.util.AlertUtils
import com.emanuelgalvao.pantry.viewmodel.ShoppingListViewModel


class ShoppingListFragment : Fragment(), View.OnClickListener {

    private lateinit var mViewModel: ShoppingListViewModel
    private var _binding: FragmentShoppingListBinding? = null
    private val binding get() = _binding!!

    private lateinit var mListener: ItemListener<ShoppingItem>
    private val mAdapter = ShoppingItemAdapter()
    private var mDeleteShoppingItemOnChecked = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentShoppingListBinding.inflate(inflater, container, false)
        val view: View = binding.root

        mViewModel = ViewModelProvider(this).get(ShoppingListViewModel::class.java)

        mViewModel.getConfiguration()

        val recycler = binding.recyclerShoppingList
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = mAdapter

        mListener = object: ItemListener<ShoppingItem> {
            override fun onDelete(item: ShoppingItem) {
                mViewModel.delete(item)
            }

            override fun onCheck(item: ShoppingItem) {
                if (!mDeleteShoppingItemOnChecked) {
                    mViewModel.update(item, true)
                } else {
                    mViewModel.delete(item)
                }
            }

            override fun onUnCheck(item: ShoppingItem) {
                mViewModel.update(item, false)
            }
        }

        binding.imageHelp.setOnClickListener(this)

        observers()

        return view
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
                AlertUtils.showSnackbar(binding.root, "Item removido com sucesso!",
                    ContextCompat.getColor(requireContext(), R.color.snack_green))
            }
        })

        mViewModel.configuration.observe(viewLifecycleOwner, {
            mDeleteShoppingItemOnChecked = it.deleteShoppingItem
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