package com.busra.countriesapplication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.busra.countriesapplication.data.local.entity.SavedCountry
import com.busra.countriesapplication.data.remote.response.Country
import com.busra.countriesapplication.data.remote.response.CountryDetail
import com.busra.countriesapplication.databinding.FragmentDetailsBinding
import com.busra.countriesapplication.ui.viewmodel.DetailsViewModel
import com.busra.countriesapplication.ui.viewmodel.SavedCountriesViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val detailsViewModel : DetailsViewModel by viewModels()
    private val savedCountriesViewModel : SavedCountriesViewModel by viewModels()
    private lateinit var detailsFrgm: FragmentDetailsBinding
    private lateinit var countryCode : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        detailsFrgm = FragmentDetailsBinding.inflate(inflater,container,false)
        return detailsFrgm.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            countryCode = DetailsFragmentArgs.fromBundle(it).countryCode
        }
        getCountryDetails(countryCode)
        viewModelSetup()
        backButton(view)
    }


    private fun viewModelSetup(){
        detailsViewModel.getCountryInformation().observe(viewLifecycleOwner) { countryDetails ->
            countryDetails?.let {
                setUp(countryDetails)
                getLoadingInformatin()
                getWebViewImage(countryDetails.image)

            }

        }
    }

    private fun setUp(countryDetail : CountryDetail){
        detailsFrgm.apply {

            detailCountryName.text = countryDetail.name
            detailCountryCode.text = countryDetail.code
            savedIcon.isChecked = savedCountriesViewModel.getSavedInformation(countryDetail.code)
            savedIcon.setOnClickListener {
                if(savedIcon.isChecked){
                    val country = Country(
                        name = countryDetail.name,
                        code = countryDetail.code
                    )
                    savedCountriesViewModel.saveCountry(country)
                    savedCountriesViewModel.editSavedInformation(country.code,true)

                    Snackbar.make(this@DetailsFragment.requireView(),"${country.name} is saved.",Snackbar.LENGTH_SHORT)
                        .show()
                }else {
                    val savedCountryList = SavedCountry(
                        savedName = countryDetail.name,
                        savedCode = countryDetail.code,
                        saved = true
                    )
                    savedCountriesViewModel.deleteCountry(savedCountryList)
                    savedCountriesViewModel.editSavedInformation(savedCountryList.savedCode,false)
                    Snackbar.make(this@DetailsFragment.requireView(),"${savedCountryList.savedName} is deleted.",Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
            detailDetailButton.setOnClickListener {
                val action = DetailsFragmentDirections.actionCountryToURL(countryDetail.wikiDataId)
                findNavController().navigate(action)
            }

        }
    }

    private fun getLoadingInformatin(){
        detailsFrgm.apply {
            detailsViewModel.getLoadingInformation().observe(viewLifecycleOwner){
                if(!it){
                    detailProgressBar.visibility = View.VISIBLE
                    detailCountryName.visibility = View.INVISIBLE
                    detailImage.visibility = View.INVISIBLE
                    detailCd.visibility = View.INVISIBLE
                    detailDetailButton.visibility = View.INVISIBLE
                    detailCountryCode.visibility = View.INVISIBLE
                }else {
                    detailProgressBar.visibility = View.INVISIBLE
                    detailImage.visibility = View.VISIBLE
                    detailCountryName.visibility = View.VISIBLE
                    detailCd.visibility = View.VISIBLE
                    detailDetailButton.visibility = View.VISIBLE
                    detailCountryCode.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun getCountryDetails(code : String) =
        detailsViewModel.getCountryDetail(code)

    private fun backButton(view:View){
        detailsFrgm.apply {
            backToHome.setOnClickListener {
                Navigation.findNavController(view).popBackStack()
            }
        }
    }

    private fun getWebViewImage(url: String){
        detailsFrgm.apply {
            detailImage.webViewClient = WebViewClient()
            detailImage.setInitialScale(1)
            detailImage.settings.useWideViewPort = true
            detailImage.settings.loadWithOverviewMode = true
            detailImage.settings.javaScriptEnabled = true
            detailImage.loadUrl(url)
        }
    }


}