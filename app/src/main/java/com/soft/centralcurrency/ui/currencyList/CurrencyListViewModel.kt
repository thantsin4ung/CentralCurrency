package com.soft.centralcurrency.ui.currencyList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.soft.centralcurrency.data.RetrofitManager
import com.soft.centralcurrency.data.api.CurrencyApi
import com.soft.centralcurrency.data.dto.ExchangeRates
import com.soft.centralcurrency.data.service.CurrencyService
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import org.joda.time.DateTime
import java.util.*

class CurrencyListViewModel(application: Application) : AndroidViewModel(application) {

    var _rates: List<ExchangeRates>? = null

    private val service: CurrencyService

    val updateDateTime = MutableLiveData<DateTime>()

    val rates = MutableLiveData<List<ExchangeRates>>()

    private val compositeDisposable = CompositeDisposable()

    init {
        service = CurrencyService(RetrofitManager.create(CurrencyApi::class.java))
    }

    fun loadExchangeRate() {
        val disposable = service.getCurrencyRate()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                updateDateTime.value = it.updatedTime
                rates.value = it.rates
                _rates = it.rates
            }, { it.printStackTrace() })
        compositeDisposable.add(disposable)
    }

    fun fitterCurrencyRates(value: String) {
        _rates?.also {
            rates.value = it.filter { rates ->
                rates.shortName.toLowerCase(Locale.ENGLISH)
                    .startsWith(value.toLowerCase(Locale.ENGLISH))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}
