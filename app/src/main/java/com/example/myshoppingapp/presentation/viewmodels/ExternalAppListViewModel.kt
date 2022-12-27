package com.example.myshoppingapp.presentation.viewmodels

import android.content.Intent
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myshoppingapp.domain.ExternalAppInfo
import com.example.myshoppingapp.domain.usecases.GetExternalAppObservableUseCase
import com.example.myshoppingapp.domain.usecases.GetLaunchIntentForPackageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

@HiltViewModel
class ExternalAppListViewModel @Inject internal constructor(
    private val getExternalAppObservableUseCase: GetExternalAppObservableUseCase,
    private val getLaunchIntentForPackageUseCase: GetLaunchIntentForPackageUseCase,
) : ViewModel() {

    private val _apps: MutableLiveData<List<ExternalAppInfo>> = MutableLiveData(listOf())
    val apps: LiveData<List<ExternalAppInfo>> = _apps

    init {
        loadExternalAppList()
    }

    private fun loadExternalAppList() {
        getExternalAppObservableUseCase.invoke()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                _apps.value = it
            }
            .subscribe({}, {})
    }

    fun getIntentForExecution(info: ExternalAppInfo, startActivity: (intent: Intent) -> Unit) {
        val intent = if (info.executable) {
            getLaunchIntentForPackageUseCase(info)
        } else {
            getMarketIntent(info.marketUri)
        }

        if (intent != null) {
            startActivity(intent)
        }
    }

    private fun getMarketIntent(marketUri: String): Intent {
        return Intent(
            Intent.ACTION_VIEW,
            marketUri.toUri()
        )
    }
}