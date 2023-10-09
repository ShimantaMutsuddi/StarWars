package com.mutsuddi_s.starwars.data.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mutsuddi_s.starwars.databinding.LoaderItemBinding

class LoaderAdapter : LoadStateAdapter<LoaderAdapter.LoaderViewHolder>() {

    class LoaderViewHolder(private val binding: LoaderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) {


            binding.apply {

                 if(loadState is LoadState.Error){
                     tvError.text=loadState.error.message
                 }
                loader.isVisible = loadState is LoadState.Loading
                tvError.isVisible = loadState is LoadState.Error

            }
        }
    }

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        val binding: LoaderItemBinding =
            LoaderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoaderViewHolder(binding)
    }
}