package com.exemplo.pessoasapp.repository

import androidx.lifecycle.LiveData
import com.exemplo.pessoasapp.data.dao.PessoaDao
import com.exemplo.pessoasapp.data.entity.Pessoa

class PessoaRepository(private val dao: PessoaDao) {

    val todasPessoas: LiveData<List<Pessoa>> = dao.buscarTodas()

    fun buscarPorNomeOuEmail(busca: String): LiveData<List<Pessoa>> {
        return dao.buscarPorNomeOuEmail(busca)
    }

    suspend fun buscarPorId(id: Long): Pessoa? {
        return dao.buscarPorId(id)
    }

    suspend fun inserir(pessoa: Pessoa): Long {
        return dao.inserir(pessoa)
    }

    suspend fun atualizar(pessoa: Pessoa) {
        dao.atualizar(pessoa)
    }

    suspend fun deletar(pessoa: Pessoa) {
        dao.deletar(pessoa)
    }

    suspend fun deletarPorId(id: Long) {
        dao.deletarPorId(id)
    }
}
