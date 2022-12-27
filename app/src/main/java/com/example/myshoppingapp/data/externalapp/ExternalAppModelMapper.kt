package com.example.myshoppingapp.data.externalapp

import com.example.myshoppingapp.domain.ExternalAppInfo
import javax.inject.Inject

class ExternalAppModelMapper @Inject internal constructor(
) {
    fun toDomainLayer(dto: ExternalAppInfoDto): ExternalAppInfo {
        return ExternalAppInfo(
            name = dto.name,
            packageName = dto.packageName,
            installable = dto.installable,
            executable = dto.executable,
            marketUri = dto.marketUri
        )
    }
}