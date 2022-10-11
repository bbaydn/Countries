package com.busra.countriesapplication.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.busra.countriesapplication.R
import com.busra.countriesapplication.data.local.entity.SavedCountry
import com.busra.countriesapplication.data.remote.response.Country
import com.busra.countriesapplication.databinding.FragmentHomeBinding
import com.busra.countriesapplication.ui.adapter.CountriesHomeAdapter
import com.busra.countriesapplication.ui.viewmodel.CountriesViewModel
import com.busra.countriesapplication.ui.viewmodel.SavedCountriesViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val countriesViewModel : CountriesViewModel by viewModels()
    private val savedCountriesViewModel : SavedCountriesViewModel by viewModels()
    private lateinit var countryFrgm: FragmentHomeBinding
    private lateinit var countriesHomeAdapter : CountriesHomeAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        countriesHomeAdapter = CountriesHomeAdapter(context)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        countryFrgm = FragmentHomeBinding.inflate(inflater,container,false)
        return countryFrgm.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setup()
        setupViewModel()

    }



    private fun setup(){
        countryFrgm.apply {
            rvCountryList.layoutManager = LinearLayoutManager(context)
            rvCountryList.adapter = countriesHomeAdapter
            rvCountryList.setHasFixedSize(true)
            countriesHomeAdapter.setOnItemClickListener(object : CountriesHomeAdapter.onItemClickListener{
                override fun onItemClicked(country: Country, boolean: Boolean) {
                    if(boolean){
                        savedCountriesViewModel.saveCountry(country)
                        countriesViewModel.editSavedInformation(country.code,boolean)
                        Snackbar.make(this@HomeFragment.requireView(),"${country.name} is saved.",Snackbar.LENGTH_SHORT)
                            .show()
                    }else {
                        val savedCountry = SavedCountry(
                            savedName = country.name,
                            savedCode = country.code,
                            saved = true
                        )
                        savedCountriesViewModel.deleteCountry(savedCountry)
                        countriesViewModel.editSavedInformation(country.code,boolean)
                        Snackbar.make(this@HomeFragment.requireView(),"${country.name} is deleted.",Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }
            })
        }
    }

    private fun setupViewModel(){
        countriesViewModel.countries.observe(viewLifecycleOwner){
            countriesHomeAdapter.submitData(viewLifecycleOwner.lifecycle,it)
        }
    }

}