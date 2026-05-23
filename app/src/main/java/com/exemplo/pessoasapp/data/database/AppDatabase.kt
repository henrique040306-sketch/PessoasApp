package com.exemplo.pessoasapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.exemplo.pessoasapp.data.dao.PessoaDao
import com.exemplo.pessoasapp.data.entity.Pessoa
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Pessoa::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun pessoaDao(): PessoaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "pessoas_database"
                )
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // Popula com dados iniciais ao criar o banco
                            INSTANCE?.let { database ->
                                CoroutineScope(Dispatchers.IO).launch {
                                    popularDadosIniciais(database.pessoaDao())
                                }
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private suspend fun popularDadosIniciais(dao: PessoaDao) {
            val pessoasIniciais = listOf(
                Pessoa(nome = "Ana Silva", email = "ana.silva@email.com", telefone = "(11) 98765-4321", idade = 28),
                Pessoa(nome = "Bruno Costa", email = "bruno.costa@email.com", telefone = "(21) 99876-5432", idade = 34),
                Pessoa(nome = "Carla Mendes", email = "carla.mendes@email.com", telefone = "(31) 97654-3210", idade = 22),
                Pessoa(nome = "Diego Rocha", email = "diego.rocha@email.com", telefone = "(41) 98765-1234", idade = 45)
            )
            pessoasIniciais.forEach { dao.inserir(it) }
        }
    }
}
