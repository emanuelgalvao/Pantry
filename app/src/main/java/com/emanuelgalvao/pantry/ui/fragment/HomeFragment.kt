package com.emanuelgalvao.pantry.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emanuelgalvao.pantry.R
import com.emanuelgalvao.pantry.databinding.FragmentHomeBinding
import com.emanuelgalvao.pantry.service.listener.ItemListener
import com.emanuelgalvao.pantry.service.model.PantryItem
import com.emanuelgalvao.pantry.service.model.ShoppingItem
import com.emanuelgalvao.pantry.ui.adapter.PantryItemAdapter
import com.emanuelgalvao.pantry.ui.adapter.ShoppingItemAdapter
import com.emanuelgalvao.pantry.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment(), View.OnClickListener {

    private lateinit var mViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    private lateinit var mPantryListener: ItemListener<PantryItem>
    private val mPantryAdapter = PantryItemAdapter()

    private lateinit var mShoppingListener: ItemListener<ShoppingItem>
    private val mShoppingAdapter = ShoppingItemAdapter()

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        mViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        mViewModel.getUserName()

        observers()

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerPantry = root.findViewById<RecyclerView>(R.id.recycler_pantry_home)
        recyclerPantry.layoutManager = LinearLayoutManager(context)
        recyclerPantry.adapter = mPantryAdapter

        mPantryListener = object: ItemListener<PantryItem> {
            override fun onDelete(item: PantryItem) {}

            override fun onCheck(item: PantryItem) {}

            override fun onUnCheck(item: PantryItem) {}
        }

        val recyclerShopping = root.findViewById<RecyclerView>(R.id.recycler_shopping_list_home)
        recyclerShopping.layoutManager = LinearLayoutManager(context)
        recyclerShopping.adapter = mShoppingAdapter

        mShoppingListener = object: ItemListener<ShoppingItem> {
            override fun onDelete(item: ShoppingItem) {}

            override fun onCheck(item: ShoppingItem) {}

            override fun onUnCheck(item: ShoppingItem) {}
        }

        root.text_see_more_due_soon.setOnClickListener(this)
        root.text_see_more_shopping_list.setOnClickListener(this)

        return root
    }

    override fun onResume() {
        super.onResume()
        mViewModel.listPantryItems()
        mPantryAdapter.attachListener(mPantryListener)
        mPantryAdapter.setShowDeleteButton(false)

        mViewModel.listShoppingItems()
        mShoppingAdapter.attachListener(mShoppingListener)
        mShoppingAdapter.setShowActions(false)
    }

    private fun observers() {
        mViewModel.userName.observe(viewLifecycleOwner, {
            text_hello.text = "Olá ${it}!"
        })

        mViewModel.pantryItemList.observe(viewLifecycleOwner, {
            if (it.count() >= 0) {
                mPantryAdapter.updateList(it)
            }
        })

        mViewModel.shoppingItemList.observe(viewLifecycleOwner, {
            if (it.count() >= 0) {
                mShoppingAdapter.updateList(it)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.text_see_more_due_soon -> Navigation.findNavController(v).navigate(R.id.action_nav_home_to_nav_pantry)
            R.id.text_see_more_shopping_list -> Navigation.findNavController(v).navigate(R.id.action_nav_home_to_nav_shopping_list)
        }
    }

}