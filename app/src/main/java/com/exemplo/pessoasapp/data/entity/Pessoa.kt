package com.exemplo.pessoasapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pessoas")
data class Pessoa(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "nome")
    val nome: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "telefone")
    val telefone: String,

    @ColumnInfo(name = "idade")
    val idade: Int
)
