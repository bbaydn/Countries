package com.busra.countriesapplication.ui.adapter

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.busra.countriesapplication.data.remote.response.Country
import com.busra.countriesapplication.databinding.CountryItemBinding
import com.busra.countriesapplication.ui.fragments.HomeFragmentDirections

class CountriesHomeAdapter(
    private val context: Context
) : PagingDataAdapter<Country,CountriesHomeAdapter.CountryViewHolder>(COUNTRY_COMPARATOR) {

    private lateinit var myListener: onItemClickListener

    private val sharedPrefernces = context.getSharedPreferences("saved",Context.MODE_PRIVATE)

    interface onItemClickListener{
        fun onItemClicked(country: Country,boolean: Boolean)
    }


    class CountryViewHolder(
        private val binding : CountryItemBinding,
        private val listener: onItemClickListener,
        private val sharedPreferences: SharedPreferences
        ) : RecyclerView.ViewHolder(binding.root){
        fun bind(country: Country){
            binding.apply {
                countryName.text = country.name
                savedIcon.isChecked = sharedPreferences.getBoolean(country.code,false)
                savedIcon.setOnClickListener {
                    listener.onItemClicked(country,savedIcon.isChecked)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val currentCountry = getItem(position)
        currentCountry?.let {
            holder.bind(currentCountry)
            holder.itemView.setOnClickListener {
                val action = HomeFragmentDirections.actionCountriesFragmentToCountryDetailFragment2(currentCountry.code)
                Navigation.findNavController(it).navigate(action)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = CountryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CountryViewHolder(binding,myListener,sharedPrefernces)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        myListener = listener
    }


    companion object {
        private val COUNTRY_COMPARATOR = object : DiffUtil.ItemCallback<Country>(){
            override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
                return oldItem.code == newItem.code
            }

            override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {
                return oldItem.code == newItem.code
            }

        }
    }
}