package com.emanuelgalvao.pantry.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.emanuelgalvao.pantry.R
import com.emanuelgalvao.pantry.databinding.FragmentPantryBinding
import com.emanuelgalvao.pantry.service.listener.ItemListener
import com.emanuelgalvao.pantry.service.model.PantryItem
import com.emanuelgalvao.pantry.ui.adapter.PantryItemAdapter
import com.emanuelgalvao.pantry.util.AlertUtils
import com.emanuelgalvao.pantry.viewmodel.PantryViewModel

class PantryFragment : Fragment(), View.OnClickListener {

    private lateinit var mViewModel: PantryViewModel
    private var _binding: FragmentPantryBinding? = null
    private val binding get() = _binding!!

    private lateinit var mListener: ItemListener<PantryItem>
    private val mAdapter = PantryItemAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentPantryBinding.inflate(inflater, container, false)
        val view: View = binding.root

        mViewModel = ViewModelProvider(this).get(PantryViewModel::class.java)

        mViewModel.getConfiguration()

        val recycler = binding.recyclerPantry
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = mAdapter

        mListener = object: ItemListener<PantryItem> {
            override fun onDelete(item: PantryItem) {
                mViewModel.delete(item)
            }

            override fun onCheck(item: PantryItem) {}

            override fun onUnCheck(item: PantryItem) {}
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
        mViewModel.pantryItemList.observe(viewLifecycleOwner, {
            if (it.count() >= 0) {
                mAdapter.updateList(it)
            }
        })

        mViewModel.validationDelete.observe(viewLifecycleOwner, {
            if (it.isSucess()) {
                AlertUtils.showSnackbar(binding.root, "Item removido com sucesso!", getColor(requireContext(), R.color.snack_green))
            }
        })

        mViewModel.configuration.observe(viewLifecycleOwner, {
            mAdapter.setDueDays(it.dueDays)
        })
    }

    private fun showHelpDialog() {
        AlertUtils.showInfoDialog(requireContext(), "Minha despensa", "Nesta tela você pode vizualizar os itens que você possui em sua despensa.\n\nItens marcados com a cor vermelha encontram-se vencidos.\n\nItens marcados com a cor amarela vencem hoje ou nos próximos 2 dias.")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.image_help) {
            showHelpDialog()
        }
    }
}