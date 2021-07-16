package com.emanuelgalvao.pantry.ui.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.emanuelgalvao.pantry.R
import com.emanuelgalvao.pantry.viewmodel.PantryViewModel

class PantryFragment : Fragment() {

    companion object {
        fun newInstance() = PantryFragment()
    }

    private lateinit var viewModel: PantryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pantry, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PantryViewModel::class.java)
        // TODO: Use the ViewModel
    }

}