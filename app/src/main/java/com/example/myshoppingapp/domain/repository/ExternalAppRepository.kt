package com.example.myshoppingapp.domain.repository

import android.content.Intent
import com.example.myshoppingapp.domain.ExternalAppInfo
import io.reactivex.rxjava3.core.Single

interface ExternalAppRepository {
    fun getExternalAppObservable(): Single<List<ExternalAppInfo>>
    fun getLaunchIntentForPackage(packageName: String): Intent?
}