package com.example.myshoppingapp.data.externalapp

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

internal class AppInstallDataSourceTest {

    private lateinit var appInstallDataSource: AppInstallDataSource

    private val mockExternalAppInfoDto: ExternalAppInfoDto = mockk()
    private val mockExecutableExternalAppInfoDto: ExternalAppInfoDto = mockk()
    private val mockContext: Context = mockk()
    private val mockIntent: Intent = mockk()
    private val mockPackageManager: PackageManager = mockk()

    @Before
    fun setUp() {
        every { mockContext.packageManager } returns mockPackageManager
        every { mockExternalAppInfoDto.packageName } returns APP_PACKAGE_NAME
        every { mockExecutableExternalAppInfoDto.packageName } returns APP_PACKAGE_NAME

        appInstallDataSource = AppInstallDataSource(mockContext)
    }

    @Test
    fun getInstallStatus_ExistLaunchIntent_ReturnsExecutableExternalAppInfoDto() {
        every { mockPackageManager.getLaunchIntentForPackage(APP_PACKAGE_NAME) } returns mockIntent
        every { mockExternalAppInfoDto.copy(executable = true) } returns mockExecutableExternalAppInfoDto

        val result = appInstallDataSource.getInstallStatus(listOf(mockExternalAppInfoDto))
        val existAppResult = result.firstOrNull { it.packageName == APP_PACKAGE_NAME }

        assertNotNull(existAppResult)
        assertEquals(
            mockExecutableExternalAppInfoDto,
            existAppResult
        )
    }

    @Test
    fun getInstallStatus_NotExistLaunchIntent_ReturnsOriginal() {
        every { mockPackageManager.getLaunchIntentForPackage(APP_PACKAGE_NAME) } returns null

        val result = appInstallDataSource.getInstallStatus(listOf(mockExternalAppInfoDto))
        val existAppResult = result.firstOrNull { it.packageName == APP_PACKAGE_NAME }

        assertNotNull(existAppResult)
        assertEquals(
            mockExternalAppInfoDto,
            existAppResult
        )
    }

    @Test
    fun getLaunchIntentForPackage_ExistLaunchIntent_ReturnsIntent() {
        every { mockPackageManager.getLaunchIntentForPackage(APP_PACKAGE_NAME) } returns mockIntent

        val result = appInstallDataSource.getLaunchIntentForPackage(APP_PACKAGE_NAME)

        assertEquals(
            mockIntent,
            result
        )
    }


    @Test
    fun getLaunchIntentForPackage_ExistLaunchIntent_ReturnsNull() {
        every { mockPackageManager.getLaunchIntentForPackage(APP_PACKAGE_NAME) } returns null

        val result = appInstallDataSource.getLaunchIntentForPackage(APP_PACKAGE_NAME)

        assertEquals(
            null,
            result
        )
    }

    companion object {
        private const val APP_PACKAGE_NAME = "APP_PACKAGE_NAME"
    }
}