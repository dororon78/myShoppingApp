package com.example.myshoppingapp.data.externalapp

import android.content.Context
import android.content.Intent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


// TODO 분리하기
class AppInstallDataSource @Inject internal constructor(
    @ApplicationContext private val context: Context
) {
    fun getInstallStatus(list: List<ExternalAppInfoDto>): List<ExternalAppInfoDto> {
        return list.map { info ->
            if (getLaunchIntentForPackage(info.packageName) != null) {
                info.copy(executable = true)
            } else {
                info
            }
        }
    }

    fun getLaunchIntentForPackage(packageName: String): Intent? {
        return context.packageManager.getLaunchIntentForPackage(packageName)
    }
}