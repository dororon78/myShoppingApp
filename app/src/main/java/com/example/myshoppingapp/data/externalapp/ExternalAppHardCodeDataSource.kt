package com.example.myshoppingapp.data.externalapp

import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ExternalAppHardCodeDataSource @Inject internal constructor(
) {
    fun getExternalAppSingle(): Single<List<ExternalAppInfoDto>> {
        val samsungPay = ExternalAppInfoDto(
            name = "삼성페이",
            packageName = "com.samsung.android.spay",
            installable = false,
            marketUri = "market://details?id=com.samsung.android.spay"
        )

        val eMartPay = ExternalAppInfoDto(
            name = "이마트페이",
            packageName = "com.emart.today",
            installable = true,
            marketUri = "market://details?id=com.emart.today"
        )

        val notExistApp = ExternalAppInfoDto(
            name = "김페이",
            packageName = "com.kim.pay",
            installable = true,
            marketUri = "market://details?id=com.kim.pay"
        )

        return Single.create {
            it.onSuccess(listOf(samsungPay, eMartPay, notExistApp))
        }
    }
}