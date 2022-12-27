package com.example.myshoppingapp.data.externalapp

import android.content.Intent
import com.example.myshoppingapp.domain.ExternalAppInfo
import com.example.myshoppingapp.domain.repository.ExternalAppRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ExternalAppRepositoryImpl @Inject internal constructor(
    private val externalAppHardCodeDataSource: ExternalAppHardCodeDataSource,
    private val appInstallDataSource: AppInstallDataSource,
    private val mapper: ExternalAppModelMapper,
) : ExternalAppRepository {
    override fun getExternalAppObservable(): Single<List<ExternalAppInfo>> {
        return externalAppHardCodeDataSource.getExternalAppSingle()
            .map { appInstallDataSource.getInstallStatus(it) }
            .map { list -> list.map { mapper.toDomainLayer(it) } }
    }

    override fun getLaunchIntentForPackage(packageName: String): Intent? {
        return appInstallDataSource.getLaunchIntentForPackage(packageName)
    }
}