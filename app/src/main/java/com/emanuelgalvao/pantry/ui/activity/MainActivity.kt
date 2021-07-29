package com.emanuelgalvao.pantry.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.emanuelgalvao.pantry.R
import com.emanuelgalvao.pantry.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val navView: BottomNavigationView = binding.navView

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_pantry,
                R.id.nav_shopping_list,
                R.id.nav_profile
            )
        )

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main)
        val navController = navHostFragment?.findNavController()

        setupActionBarWithNavController(navController!!, appBarConfiguration)
        navView.setupWithNavController(navController)

        binding.fabAdd.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.fab_add) {
            openDialogList()
        }
    }

    private fun openDialogList() {

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        val options = arrayOf("Adicionar na despensa", "Adicionar na lista de compras")
        builder.setItems(options) { _, which ->
            when (which) {
                0 -> startActivity(Intent(this, ReadCodeActivity::class.java))
                1 -> startActivity(Intent(this, ShoppingListFormActivity::class.java))
            }
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}