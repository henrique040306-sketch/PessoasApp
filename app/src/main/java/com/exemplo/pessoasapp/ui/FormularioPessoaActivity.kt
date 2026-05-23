package com.exemplo.pessoasapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.exemplo.pessoasapp.data.entity.Pessoa
import com.exemplo.pessoasapp.databinding.ActivityFormularioPessoaBinding
import com.exemplo.pessoasapp.ui.viewmodel.PessoaViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class FormularioPessoaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormularioPessoaBinding
    private val viewModel: PessoaViewModel by viewModels()

    private var pessoaId: Long = -1L
    private var pessoaAtual: Pessoa? = null

    companion object {
        const val EXTRA_PESSOA_ID = "extra_pessoa_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormularioPessoaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        pessoaId = intent.getLongExtra(EXTRA_PESSOA_ID, -1L)
        val modoEdicao = pessoaId != -1L

        supportActionBar?.title = if (modoEdicao) "Editar Pessoa" else "Nova Pessoa"

        if (modoEdicao) {
            carregarPessoa(pessoaId)
        }

        binding.btnSalvar.setOnClickListener {
            salvar()
        }
    }

    private fun carregarPessoa(id: Long) {
        lifecycleScope.launch {
            pessoaAtual = viewModel.buscarPorId(id)
            pessoaAtual?.let { preencherCampos(it) }
        }
    }

    private fun preencherCampos(pessoa: Pessoa) {
        binding.apply {
            etNome.setText(pessoa.nome)
            etEmail.setText(pessoa.email)
            etTelefone.setText(pessoa.telefone)
            etIdade.setText(pessoa.idade.toString())
        }
    }

    private fun salvar() {
        val nome = binding.etNome.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val telefone = binding.etTelefone.text.toString().trim()
        val idadeStr = binding.etIdade.text.toString().trim()

        // Validações
        var valido = true

        if (nome.isBlank()) {
            binding.tilNome.error = "Nome é obrigatório"
            valido = false
        } else {
            binding.tilNome.error = null
        }

        if (email.isBlank()) {
            binding.tilEmail.error = "Email é obrigatório"
            valido = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.error = "Email inválido"
            valido = false
        } else {
            binding.tilEmail.error = null
        }

        if (telefone.isBlank()) {
            binding.tilTelefone.error = "Telefone é obrigatório"
            valido = false
        } else {
            binding.tilTelefone.error = null
        }

        if (idadeStr.isBlank()) {
            binding.tilIdade.error = "Idade é obrigatória"
            valido = false
        } else {
            val idade = idadeStr.toIntOrNull()
            if (idade == null || idade <= 0 || idade > 150) {
                binding.tilIdade.error = "Idade inválida"
                valido = false
            } else {
                binding.tilIdade.error = null
            }
        }

        if (!valido) return

        val idade = idadeStr.toInt()

        if (pessoaId != -1L && pessoaAtual != null) {
            // Edição
            val pessoaAtualizada = pessoaAtual!!.copy(
                nome = nome,
                email = email,
                telefone = telefone,
                idade = idade
            )
            viewModel.atualizar(pessoaAtualizada)
            finalizarComSucesso("$nome atualizado com sucesso!")
        } else {
            // Nova pessoa
            val novaPessoa = Pessoa(
                nome = nome,
                email = email,
                telefone = telefone,
                idade = idade
            )
            viewModel.inserir(novaPessoa)
            finalizarComSucesso("$nome adicionado com sucesso!")
        }
    }

    private fun finalizarComSucesso(mensagem: String) {
        val resultIntent = Intent()
        resultIntent.putExtra("mensagem", mensagem)
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
