package com.soft.centralcurrency.ui.currencyList

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.soft.centralcurrency.R
import kotlinx.android.synthetic.main.currency_list_fragment.*

class CurrencyListFragment : Fragment() {

    private lateinit var viewModel: CurrencyListViewModel
    private lateinit var adapter: CurrencyAdapter

    private val adapterDataObserver = object : RecyclerView.AdapterDataObserver() {
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            recyclerview?.scrollToPosition(0)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.currency_list_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MobileAds.initialize(this.context){}

        adapter = CurrencyAdapter()
        viewModel = ViewModelProvider(this)[CurrencyListViewModel::class.java]
        viewModel.rates.observe(this, Observer {
            adapter.submitList(it)
            swipeRefresh.isRefreshing = false
        })

        viewModel.updateDateTime.observe(this, Observer {
            showTime.text = it.toString("yyyy/MM/dd hh:mm a")
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        recyclerview.apply {
            setHasFixedSize(true)
            adapter = this@CurrencyListFragment.adapter
        }

        Log.d("TAG", recyclerview.size.toString())

        swipeRefresh.setOnRefreshListener {
            viewModel.loadExchangeRate()
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            swipeRefresh.setColorSchemeColors(resources.getColor(R.color.colorPrimary, null))
        } else {
            swipeRefresh.setColorSchemeColors(resources.getColor(R.color.colorPrimary))
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        swipeRefresh.isRefreshing = true
        viewModel.loadExchangeRate()
    }

    override fun onStart() {
        super.onStart()
        adapter.registerAdapterDataObserver(adapterDataObserver)

    }

    override fun onStop() {
        super.onStop()
        adapter.unregisterAdapterDataObserver(adapterDataObserver)
    }

}
