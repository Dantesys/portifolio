package com.dantesys.portifolio.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dantesys.portifolio.data.model.Repo
import com.dantesys.portifolio.databinding.ItemRepoBinding
import com.squareup.picasso.Picasso

class Adapter:ListAdapter<Repo,Adapter.ViewHolder>(DiffCallback()){
    var listenerShare: (View,texto:String) -> Unit = { view: View, s: String -> }
    inner class ViewHolder(private val binding: ItemRepoBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item:Repo){
            binding.tvTitle.text = item.name
            binding.tvLang.text = item.language
            binding.tvDesc.text = item.description
            binding.chip4.text = item.stargazersCount.toString()
            Picasso.get().load(item.owner.avatarURL).into(binding.ivDono)
            binding.Card.setOnClickListener {
                listenerShare(it,item.htmlURL)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRepoBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class DiffCallback : DiffUtil.ItemCallback<Repo>() {
    override fun areItemsTheSame(oldItem: Repo, newItem: Repo) =
        oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Repo, newItem: Repo) =
        oldItem == newItem
}