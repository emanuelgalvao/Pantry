package com.emanuelgalvao.pantry.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.emanuelgalvao.pantry.R
import com.emanuelgalvao.pantry.databinding.FragmentProfileBinding
import com.emanuelgalvao.pantry.ui.activity.ConfigurationActivity
import com.emanuelgalvao.pantry.ui.activity.LoginActivity
import com.emanuelgalvao.pantry.ui.activity.UserDataActivity
import com.emanuelgalvao.pantry.util.AlertUtils
import com.emanuelgalvao.pantry.viewmodel.ProfileViewModel

class ProfileFragment : Fragment(), View.OnClickListener {

    private lateinit var mViewModel: ProfileViewModel
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view: View = binding.root

        mViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        binding.textMyData.setOnClickListener(this)
        binding.textConfiguration.setOnClickListener(this)
        binding.textInfo.setOnClickListener(this)
        binding.textLogOut.setOnClickListener(this)

        observers()

        return view
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.text_my_data -> startActivity(Intent(activity, UserDataActivity::class.java))
            R.id.text_configuration -> startActivity(Intent(activity, ConfigurationActivity::class.java))
            R.id.text_info -> mViewModel.getVersionName()
            R.id.text_log_out -> logout()
        }
    }

    private fun observers() {
        mViewModel.logout.observe(viewLifecycleOwner, {
            if (it.isSucess()) {
                startActivity(Intent(context, LoginActivity::class.java))
                activity?.finish()
            }
        })

        mViewModel.versionName.observe(viewLifecycleOwner, {
            showInfoDialog(it)
        })
    }

    private fun showInfoDialog(versionName: String) {
        val appName = getString(R.string.app_name)
        AlertUtils.showInfoDialog(requireContext(), "Sobre", "${appName}\n\nVersão: ${versionName}\n\nDesenvolvedor:\nEmanuel Rodrigues Galvão")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun logout() {
        mViewModel.logout()
    }

}