package com.busra.countriesapplication.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.busra.countriesapplication.data.local.entity.SavedCountry
import com.busra.countriesapplication.databinding.FragmentSavedBinding
import com.busra.countriesapplication.ui.adapter.SavedCountriesAdapter
import com.busra.countriesapplication.ui.viewmodel.SavedCountriesViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedFragment : Fragment() {

    private lateinit var savedFrgm: FragmentSavedBinding
    private val savedCountriesViewModel : SavedCountriesViewModel by viewModels()
    private lateinit var savedCountriesAdapter : SavedCountriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        savedCountriesAdapter = SavedCountriesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        savedFrgm = FragmentSavedBinding.inflate(inflater,container,false)
        return savedFrgm.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setup()
        setupViewModel()
    }

    private fun setup(){
        savedFrgm.apply {
            rvSavedCountryList.layoutManager = LinearLayoutManager(context)
            rvSavedCountryList.adapter = savedCountriesAdapter
            rvSavedCountryList.setHasFixedSize(true)
            savedCountriesAdapter.setOnItemClickListener(object : SavedCountriesAdapter.onItemClickListener{
                override fun onItemClicked(savedCountry: SavedCountry) {
                    savedCountriesViewModel.deleteCountry(savedCountry)
                    savedCountriesViewModel.editSavedInformation(savedCountry.savedCode,false)
                    Snackbar.make(this@SavedFragment.requireView(), "${savedCountry.savedName} is deleted.", Toast.LENGTH_SHORT)
                        .show()
                }

            })
        }
    }

    private fun setupViewModel(){
        savedCountriesViewModel.getAllSavedCountries.observe(viewLifecycleOwner){
            savedCountriesAdapter.submitList(it)
        }
    }

}