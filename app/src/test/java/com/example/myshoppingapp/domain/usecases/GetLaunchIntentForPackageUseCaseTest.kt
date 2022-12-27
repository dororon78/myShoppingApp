package com.example.myshoppingapp.domain.usecases

import android.content.Intent
import com.example.myshoppingapp.domain.ExternalAppInfo
import com.example.myshoppingapp.domain.repository.ExternalAppRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

internal class GetLaunchIntentForPackageUseCaseTest {

    private lateinit var getLaunchIntentForPackageUseCase: GetLaunchIntentForPackageUseCase

    private val mockIntent: Intent = mockk()
    private val mockExternalAppInfo: ExternalAppInfo = mockk()
    private val mockExternalAppRepository: ExternalAppRepository = mockk()

    @Before
    fun setUp() {
        every { mockExternalAppInfo.packageName } returns PACKAGE_NAME

        getLaunchIntentForPackageUseCase =
            GetLaunchIntentForPackageUseCase(mockExternalAppRepository)
    }

    @Test
    fun invoke_ExistLaunchIntent_ReturnIntent() {
        every { mockExternalAppRepository.getLaunchIntentForPackage(PACKAGE_NAME) } returns mockIntent

        val result = getLaunchIntentForPackageUseCase.invoke(mockExternalAppInfo)

        assertEquals(
            mockIntent,
            result
        )
        verify { mockExternalAppRepository.getLaunchIntentForPackage(PACKAGE_NAME) }
    }

    @Test
    fun invoke_NotExistLaunchIntent_ReturnNull() {
        every { mockExternalAppRepository.getLaunchIntentForPackage(PACKAGE_NAME) } returns null

        val result = getLaunchIntentForPackageUseCase.invoke(mockExternalAppInfo)

        assertNull(result)
        verify { mockExternalAppRepository.getLaunchIntentForPackage(PACKAGE_NAME) }
    }


    companion object {
        const val PACKAGE_NAME = "PACKAGE_NAME"
    }
}