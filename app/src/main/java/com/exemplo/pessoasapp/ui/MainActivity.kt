package com.exemplo.pessoasapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.exemplo.pessoasapp.R
import com.exemplo.pessoasapp.data.entity.Pessoa
import com.exemplo.pessoasapp.databinding.ActivityMainBinding
import com.exemplo.pessoasapp.ui.adapter.PessoaAdapter
import com.exemplo.pessoasapp.ui.viewmodel.PessoaViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: PessoaViewModel by viewModels()
    private lateinit var adapter: PessoaAdapter

    private val formularioLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val snackMsg = result.data?.getStringExtra("mensagem") ?: "Operação realizada!"
            Snackbar.make(binding.root, snackMsg, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        configurarRecyclerView()
        configurarObservers()
        configurarFab()
    }

    private fun configurarRecyclerView() {
        adapter = PessoaAdapter(
            onEditar = { pessoa -> abrirFormulario(pessoa) },
            onDeletar = { pessoa -> confirmarDelecao(pessoa) }
        )

        binding.recyclerView.apply {
            this.adapter = this@MainActivity.adapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(false)
        }
    }

    private fun configurarObservers() {
        viewModel.pessoasExibidas.observe(this) { pessoas ->
            adapter.submitList(pessoas)

            val listaVazia = pessoas.isEmpty()
            binding.recyclerView.isVisible = !listaVazia
            binding.layoutVazio.isVisible = listaVazia

            binding.tvContador.text = when (pessoas.size) {
                0 -> "Nenhuma pessoa"
                1 -> "1 pessoa"
                else -> "${pessoas.size} pessoas"
            }
        }
    }

    private fun configurarFab() {
        binding.fab.setOnClickListener {
            abrirFormulario(null)
        }
    }

    private fun abrirFormulario(pessoa: Pessoa?) {
        val intent = Intent(this, FormularioPessoaActivity::class.java)
        pessoa?.let {
            intent.putExtra(FormularioPessoaActivity.EXTRA_PESSOA_ID, it.id)
        }
        formularioLauncher.launch(intent)
    }

    private fun confirmarDelecao(pessoa: Pessoa) {
        AlertDialog.Builder(this)
            .setTitle("Excluir pessoa")
            .setMessage("Deseja excluir ${pessoa.nome}?")
            .setPositiveButton("Excluir") { _, _ ->
                viewModel.deletar(pessoa)
                Snackbar.make(binding.root, "${pessoa.nome} excluído!", Snackbar.LENGTH_LONG)
                    .setAction("Desfazer") { viewModel.inserir(pessoa) }
                    .show()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.queryHint = "Buscar por nome ou email..."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.definirTermoBusca(newText.orEmpty())
                return true
            }
        })

        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean = true
            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                viewModel.definirTermoBusca("")
                return true
            }
        })

        return true
    }
}
