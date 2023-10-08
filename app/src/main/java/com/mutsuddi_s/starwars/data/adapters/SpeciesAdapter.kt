package com.mutsuddi_s.starwars.data.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mutsuddi_s.starwars.data.model.people.SpeciesResponse
import com.mutsuddi_s.starwars.databinding.SpeciesRowBinding


class SpeciesAdapter :
    ListAdapter<SpeciesResponse, SpeciesAdapter.MyViewHolder>(CHARACTER_COMPARATOR) {

    inner class MyViewHolder(private val binding: SpeciesRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(speciesResponse: SpeciesResponse?) {
            binding.textViewNameValue.text = speciesResponse?.name
            binding.textViewClassificationValue.text = speciesResponse?.classification
            binding.textViewDesignationValue.text = speciesResponse?.designation
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            SpeciesRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val species = getItem(position)
        holder.bind(species)
    }

    companion object {
        private val CHARACTER_COMPARATOR = object : DiffUtil.ItemCallback<SpeciesResponse>() {
            override fun areItemsTheSame(oldItem: SpeciesResponse, newItem: SpeciesResponse): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: SpeciesResponse, newItem: SpeciesResponse): Boolean {
                return oldItem == newItem
            }
        }
    }
}