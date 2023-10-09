package com.mutsuddi_s.starwars.data.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mutsuddi_s.starwars.data.model.starships.Starships
import com.mutsuddi_s.starwars.databinding.StarshipRowBinding

class StarshipAdapter :
    PagingDataAdapter<Starships, StarshipAdapter.MyViewHolder>(COMPARATOR) {



    inner class MyViewHolder(private val binding: StarshipRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(starships: Starships?) {
            binding.apply {
                textViewNameValue.text=starships?.name
                textViewModelValue.text=starships?.model


            }




        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            StarshipRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val starships = getItem(position)
        holder.bind(starships)

    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Starships>() {
            override fun areItemsTheSame(oldItem: Starships, newItem: Starships): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Starships, newItem: Starships): Boolean {
                return oldItem == newItem
            }
        }
    }


}