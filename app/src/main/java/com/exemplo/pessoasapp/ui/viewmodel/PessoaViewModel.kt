package com.exemplo.pessoasapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.exemplo.pessoasapp.data.database.AppDatabase
import com.exemplo.pessoasapp.data.entity.Pessoa
import com.exemplo.pessoasapp.repository.PessoaRepository
import kotlinx.coroutines.launch

class PessoaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PessoaRepository
    val todasPessoas: LiveData<List<Pessoa>>

    private val _termoBusca = MutableLiveData<String>("")
    val termoBusca: LiveData<String> = _termoBusca

    init {
        val dao = AppDatabase.getInstance(application).pessoaDao()
        repository = PessoaRepository(dao)
        todasPessoas = repository.todasPessoas
    }

    val pessoasExibidas: LiveData<List<Pessoa>> = _termoBusca.switchMap { termo ->
        if (termo.isBlank()) {
            repository.todasPessoas
        } else {
            repository.buscarPorNomeOuEmail(termo)
        }
    }

    fun definirTermoBusca(termo: String) {
        _termoBusca.value = termo
    }

    fun inserir(pessoa: Pessoa) = viewModelScope.launch {
        repository.inserir(pessoa)
    }

    fun atualizar(pessoa: Pessoa) = viewModelScope.launch {
        repository.atualizar(pessoa)
    }

    fun deletar(pessoa: Pessoa) = viewModelScope.launch {
        repository.deletar(pessoa)
    }

    suspend fun buscarPorId(id: Long): Pessoa? {
        return repository.buscarPorId(id)
    }
}