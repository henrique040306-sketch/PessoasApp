package com.exemplo.pessoasapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.exemplo.pessoasapp.data.entity.Pessoa

@Dao
interface PessoaDao {

    @Query("SELECT * FROM pessoas ORDER BY nome ASC")
    fun buscarTodas(): LiveData<List<Pessoa>>

    @Query("SELECT * FROM pessoas WHERE nome LIKE '%' || :busca || '%' OR email LIKE '%' || :busca || '%' ORDER BY nome ASC")
    fun buscarPorNomeOuEmail(busca: String): LiveData<List<Pessoa>>

    @Query("SELECT * FROM pessoas WHERE id = :id")
    suspend fun buscarPorId(id: Long): Pessoa?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserir(pessoa: Pessoa): Long

    @Update
    suspend fun atualizar(pessoa: Pessoa)

    @Delete
    suspend fun deletar(pessoa: Pessoa)

    @Query("DELETE FROM pessoas WHERE id = :id")
    suspend fun deletarPorId(id: Long)
}
