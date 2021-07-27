package com.emanuelgalvao.pantry.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.emanuelgalvao.pantry.R
import com.emanuelgalvao.pantry.ui.activity.LoginActivity
import com.emanuelgalvao.pantry.ui.activity.UserDataActivity
import com.emanuelgalvao.pantry.util.AlertUtils
import com.emanuelgalvao.pantry.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : Fragment(), View.OnClickListener {

    private lateinit var mViewModel: ProfileViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        mViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        root.text_my_data.setOnClickListener(this)
        root.text_info.setOnClickListener(this)
        root.text_log_out.setOnClickListener(this)

        observers()

        return root
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.text_my_data -> startActivity(Intent(activity, UserDataActivity::class.java))
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

    private fun logout() {
        mViewModel.logout()
    }

}