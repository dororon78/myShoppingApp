package com.example.myshoppingapp.domain.usecases

import android.content.Intent
import com.example.myshoppingapp.domain.ExternalAppInfo
import com.example.myshoppingapp.domain.repository.ExternalAppRepository
import javax.inject.Inject

class GetLaunchIntentForPackageUseCase @Inject internal constructor(
    private val repository: ExternalAppRepository
) {
    operator fun invoke(info: ExternalAppInfo): Intent? {
        return repository.getLaunchIntentForPackage(info.packageName)
    }
}