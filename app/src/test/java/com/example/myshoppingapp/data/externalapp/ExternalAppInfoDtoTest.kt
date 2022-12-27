package com.example.myshoppingapp.data.externalapp


import junit.framework.TestCase.assertEquals
import org.junit.Test

internal class ExternalAppInfoDtoTest {

    private lateinit var externalAppInfoDto: ExternalAppInfoDto

    @Test
    fun getName_CreateInstanceWithName_ReturnsOriginal() {
        externalAppInfoDto = ExternalAppInfoDto(
            name = APP_NAME,
            packageName = APP_PACKAGE_NAME,
            installable = APP_INSTALLABLE,
            marketUri = MARKET_URI,
        )

        assertEquals(
            APP_NAME,
            externalAppInfoDto.name
        )
    }

    @Test
    fun getPackageName_CreateInstanceWithPackageName_ReturnsOriginal() {
        externalAppInfoDto = ExternalAppInfoDto(
            name = APP_NAME,
            packageName = APP_PACKAGE_NAME,
            installable = APP_INSTALLABLE,
            marketUri = MARKET_URI,
        )

        assertEquals(
            APP_PACKAGE_NAME,
            externalAppInfoDto.packageName,
        )
    }

    @Test
    fun getInstallable_CreateInstanceWithInstallable_ReturnsOriginal() {
        externalAppInfoDto = ExternalAppInfoDto(
            name = APP_NAME,
            packageName = APP_PACKAGE_NAME,
            installable = APP_INSTALLABLE,
            marketUri = MARKET_URI,
        )

        assertEquals(
            APP_INSTALLABLE,
            externalAppInfoDto.installable,
        )
    }

    @Test
    fun getExecutable_CreateInstanceWithExecutable_ReturnsOriginal() {
        externalAppInfoDto = ExternalAppInfoDto(
            name = APP_NAME,
            packageName = APP_PACKAGE_NAME,
            installable = APP_INSTALLABLE,
            executable = APP_EXECUTABLE,
            marketUri = MARKET_URI,
        )

        assertEquals(
            APP_EXECUTABLE,
            externalAppInfoDto.executable,
        )
    }

    @Test
    fun getExecutable_CreateInstanceWithoutExecutable_ReturnsFalseAsDefault() {
        externalAppInfoDto = ExternalAppInfoDto(
            name = APP_NAME,
            packageName = APP_PACKAGE_NAME,
            installable = APP_INSTALLABLE,
            marketUri = MARKET_URI,
        )

        assertEquals(
            false,
            externalAppInfoDto.executable
        )
    }


    @Test
    fun copy_CreateInstanceWithArguments_ReturnsOriginals() {
        externalAppInfoDto = ExternalAppInfoDto(
            name = APP_NAME,
            packageName = APP_PACKAGE_NAME,
            installable = APP_INSTALLABLE,
            executable = APP_EXECUTABLE,
            marketUri = MARKET_URI,
        )

        val result = externalAppInfoDto.copy()

        assertEquals(
            externalAppInfoDto.name,
            result.name
        )
        assertEquals(
            externalAppInfoDto.packageName,
            result.packageName
        )
        assertEquals(
            externalAppInfoDto.installable,
            result.installable
        )
        assertEquals(
            externalAppInfoDto.executable,
            result.executable
        )
        assertEquals(
            externalAppInfoDto.marketUri,
            result.marketUri
        )
    }

    companion object {
        private const val APP_NAME = "APP_NAME"
        private const val APP_PACKAGE_NAME = "APP_PACKAGE_NAME"
        private const val MARKET_URI = "MARKET_URI"
        private const val APP_INSTALLABLE = false
        private const val APP_EXECUTABLE = true
    }
}