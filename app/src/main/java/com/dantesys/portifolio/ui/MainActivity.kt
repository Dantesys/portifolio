package com.dantesys.portifolio.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import com.dantesys.portifolio.R
import com.dantesys.portifolio.core.createDialog
import com.dantesys.portifolio.core.createProgessBar
import com.dantesys.portifolio.core.hideSoftKeyboard
import com.dantesys.portifolio.databinding.ActivityMainBinding
import com.dantesys.portifolio.presentation.MainViewModel
import com.dantesys.portifolio.util.Image
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private val dialog by lazy { createProgessBar() }
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val mainViewModel:MainViewModel by viewModel()
    private val adapter by lazy { Adapter() }
    private val default by lazy { "Dantesys" }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.tbMain)
        binding.rvRepos.adapter = adapter
        mainViewModel.getRepoList(default)
        mainViewModel.repos.observe(this){
            when(it){
                MainViewModel.State.Loading -> {
                    dialog.show()
                }
                is MainViewModel.State.Error -> {
                    createDialog {
                        setMessage(it.error.message)
                    }.show()
                    dialog.dismiss()
                }
                is MainViewModel.State.Success -> {
                    dialog.dismiss()
                    adapter.submitList(it.list)
                }
            }
        }
        listeners()
    }
    private fun listeners(){
        adapter.listenerShare = { view: View, s: String ->
            Image.share(this,view,s)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.mainmenu,menu)
        val sv = (menu.findItem(R.id.busca).actionView as SearchView)
        sv.setOnQueryTextListener(this)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        binding.root.hideSoftKeyboard()
        if (query != null && query != "") {
            mainViewModel.getRepoList(query)
        }else{
            mainViewModel.getRepoList(default)
        }
        return true
    }
    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText == null && newText == "") {
            mainViewModel.getRepoList(default)
        }
        return false
    }
    companion object val TAG = "TAG"
}
