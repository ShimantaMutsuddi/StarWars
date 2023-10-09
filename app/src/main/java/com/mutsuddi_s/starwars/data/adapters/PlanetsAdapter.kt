package com.mutsuddi_s.starwars.data.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mutsuddi_s.starwars.data.model.planets.Planets
import com.mutsuddi_s.starwars.databinding.PlanetRowBinding

class PlanetsAdapter :
    PagingDataAdapter<Planets, PlanetsAdapter.MyViewHolder>(COMPARATOR) {



    inner class MyViewHolder(private val binding: PlanetRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(planets: Planets?) {
            binding.apply {
                textViewNameValue.text=planets?.name
                textViewClimateValue.text=planets?.climate
                textViewDiameterValue.text=planets?.diameter
                textViewOrbitValue.text=planets?.orbital_period
                textViewRotationValue.text=planets?.rotation_period
                textViewPopulationValue.text=planets?.population

            }




        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            PlanetRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val planets = getItem(position)
        holder.bind(planets)

    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Planets>() {
            override fun areItemsTheSame(oldItem: Planets, newItem: Planets): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Planets, newItem: Planets): Boolean {
                return oldItem == newItem
            }
        }
    }


}