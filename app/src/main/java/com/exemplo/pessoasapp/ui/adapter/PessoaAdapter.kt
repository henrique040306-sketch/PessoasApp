package com.exemplo.pessoasapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.exemplo.pessoasapp.data.entity.Pessoa
import com.exemplo.pessoasapp.databinding.ItemPessoaBinding

class PessoaAdapter(
    private val onEditar: (Pessoa) -> Unit,
    private val onDeletar: (Pessoa) -> Unit
) : ListAdapter<Pessoa, PessoaAdapter.PessoaViewHolder>(PessoaDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PessoaViewHolder {
        val binding = ItemPessoaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PessoaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PessoaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PessoaViewHolder(
        private val binding: ItemPessoaBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(pessoa: Pessoa) {
            binding.apply {
                // Iniciais para o avatar
                val iniciais = pessoa.nome
                    .split(" ")
                    .take(2)
                    .joinToString("") { it.first().uppercase() }
                tvIniciais.text = iniciais

                tvNome.text = pessoa.nome
                tvEmail.text = pessoa.email
                tvTelefone.text = pessoa.telefone
                tvIdade.text = "${pessoa.idade} anos"

                btnEditar.setOnClickListener { onEditar(pessoa) }
                btnDeletar.setOnClickListener { onDeletar(pessoa) }
            }
        }
    }

    class PessoaDiffCallback : DiffUtil.ItemCallback<Pessoa>() {
        override fun areItemsTheSame(oldItem: Pessoa, newItem: Pessoa): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Pessoa, newItem: Pessoa): Boolean {
            return oldItem == newItem
        }
    }
}
