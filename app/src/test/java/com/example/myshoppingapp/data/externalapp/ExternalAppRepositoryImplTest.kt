package com.example.myshoppingapp.data.externalapp

import android.content.Intent
import com.example.myshoppingapp.domain.ExternalAppInfo
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

internal class ExternalAppRepositoryImplTest {

    private lateinit var externalAppRepositoryImpl: ExternalAppRepositoryImpl

    private val mockIntent: Intent = mockk()
    private val mockExternalAppInfo: ExternalAppInfo = mockk()
    private val mockExternalAppInfoDto: ExternalAppInfoDto = mockk()
    private val mockExternalAppHardCodeDataSource: ExternalAppHardCodeDataSource = mockk()
    private val mockAppInstallDataSource: AppInstallDataSource = mockk()
    private val mockExternalAppModelMapper: ExternalAppModelMapper = mockk()

    @Before
    fun setUp() {
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setSingleSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }

        every { mockExternalAppHardCodeDataSource.getExternalAppSingle() } returns Single.error(
            Throwable("Test")
        )

        every { mockAppInstallDataSource.getInstallStatus(listOf(mockExternalAppInfoDto)) } returns listOf(
            mockExternalAppInfoDto
        )
        every { mockExternalAppModelMapper.toDomainLayer(mockExternalAppInfoDto) } returns mockExternalAppInfo

        externalAppRepositoryImpl = ExternalAppRepositoryImpl(
            mockExternalAppHardCodeDataSource,
            mockAppInstallDataSource,
            mockExternalAppModelMapper
        )
    }

    @Test
    fun getExternalAppObservable_OnSuccessSingle_ReturnListOfExternalAppInfo() {
        every { mockExternalAppHardCodeDataSource.getExternalAppSingle() } returns Single.just(
            listOf(mockExternalAppInfoDto)
        )

        externalAppRepositoryImpl.getExternalAppObservable()
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue(listOf(mockExternalAppInfo))

        verify { mockExternalAppHardCodeDataSource.getExternalAppSingle() }
        verify { mockAppInstallDataSource.getInstallStatus(listOf(mockExternalAppInfoDto)) }
        verify { mockExternalAppModelMapper.toDomainLayer(mockExternalAppInfoDto) }
    }

    @Test
    fun getExternalAppObservable_OnErrorSingle_ReturnThrowable() {
        every { mockExternalAppHardCodeDataSource.getExternalAppSingle() } returns Single.error(
            Throwable("Test")
        )

        externalAppRepositoryImpl.getExternalAppObservable()
            .test()
            .assertError(Throwable::class.java)

        verify { mockExternalAppHardCodeDataSource.getExternalAppSingle() }
        verify(exactly = 0) {
            mockAppInstallDataSource.getInstallStatus(
                listOf(
                    mockExternalAppInfoDto
                )
            )
        }
        verify(exactly = 0) { mockExternalAppModelMapper.toDomainLayer(mockExternalAppInfoDto) }
    }

    @Test
    fun getLaunchIntentForPackage_ExistLaunchIntent_ReturnIntent() {
        every { mockAppInstallDataSource.getLaunchIntentForPackage(PACKAGE_NAME) } returns mockIntent

        val result = externalAppRepositoryImpl.getLaunchIntentForPackage(PACKAGE_NAME)

        assertEquals(
            mockIntent,
            result
        )
        verify { mockAppInstallDataSource.getLaunchIntentForPackage(PACKAGE_NAME) }
    }

    @Test
    fun getLaunchIntentForPackage_NotExistLaunchIntent_ReturnNull() {
        every { mockAppInstallDataSource.getLaunchIntentForPackage(PACKAGE_NAME) } returns null

        val result = externalAppRepositoryImpl.getLaunchIntentForPackage(PACKAGE_NAME)

        assertNull(result)
        verify { mockAppInstallDataSource.getLaunchIntentForPackage(PACKAGE_NAME) }
    }

    companion object {
        const val PACKAGE_NAME = "PACKAGE_NAME"
    }
}