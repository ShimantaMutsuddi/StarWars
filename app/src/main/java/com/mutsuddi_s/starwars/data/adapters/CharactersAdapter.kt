package com.mutsuddi_s.starwars.data.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mutsuddi_s.starwars.data.model.people.Character
import com.mutsuddi_s.starwars.databinding.CharactersRowBinding

class CharactersAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<Character, CharactersAdapter.MyViewHolder>(CHARACTER_COMPARATOR) {

    interface OnItemClickListener {
        fun onItemClick(character: Character)
    }

    inner class MyViewHolder(private val binding: CharactersRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character?) {
            binding.textViewNameValue.text = character?.name
            binding.textViewBirthYearValue.text = character?.birth_year
            binding.textViewGenderValue.text = character?.gender

            binding.root.setOnClickListener {
                // Invoke the callback method with the clicked item
                listener.onItemClick(character!!)
            }


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            CharactersRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val character = getItem(position)
        holder.bind(character)

    }

    companion object {
         val CHARACTER_COMPARATOR = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem == newItem
            }
        }
    }


}