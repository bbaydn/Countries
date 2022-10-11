package com.busra.countriesapplication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.Navigation
import com.busra.countriesapplication.databinding.FragmentUrlBinding

class UrlFragment : Fragment() {

    private lateinit var urlFrgm: FragmentUrlBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        urlFrgm = FragmentUrlBinding.inflate(inflater,container,false)
        return urlFrgm.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val wikiDataId = UrlFragmentArgs.fromBundle(it).countryWikiData
            getWebView(wikiDataId = wikiDataId)
        }
        backButton(view)

    }


    private fun getWebView(wikiDataId: String){
        urlFrgm.apply {
            webWebView.webViewClient = WebViewClient()
            webWebView.setInitialScale(1)
            webWebView.settings.useWideViewPort = true
            webWebView.settings.loadWithOverviewMode = true
            webWebView.settings.javaScriptEnabled = true
            webWebView.loadUrl("https://www.wikidata.org/wiki/${wikiDataId}")
        }
    }

    private fun backButton(view:View){
        urlFrgm.apply {
            webBackButton.setOnClickListener {
                Navigation.findNavController(view).popBackStack()
            }
        }
    }

}

