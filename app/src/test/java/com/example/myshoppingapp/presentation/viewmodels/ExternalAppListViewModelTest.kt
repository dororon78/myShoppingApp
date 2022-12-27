package com.example.myshoppingapp.presentation.viewmodels

import android.content.Intent
import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.myshoppingapp.domain.ExternalAppInfo
import com.example.myshoppingapp.domain.usecases.GetExternalAppObservableUseCase
import com.example.myshoppingapp.domain.usecases.GetLaunchIntentForPackageUseCase
import io.mockk.*
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.*


internal class ExternalAppListViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var launchExternalAppViewModel: ExternalAppListViewModel

    private val mockAppLaunchIntent: Intent = mockk()
    private val mockUri: Uri = mockk()
    private val mockStartActivityLambda: (intent: Intent) -> Unit = mockk()
    private val mockExternalAppInfo: ExternalAppInfo = mockk()
    private val getExternalAppObservableUseCase: GetExternalAppObservableUseCase = mockk()
    private val getLaunchIntentForPackageUseCase: GetLaunchIntentForPackageUseCase = mockk()

    @Before
    fun setUp() {
        mockkStatic(Uri::class)
        every { Uri.parse(any()) } returns mockUri

        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setSingleSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @After
    fun tearDown() {
        unmockkStatic(Uri::class)
    }

    @Test
    fun getApps_OnSuccessLoadDataWithListOfExternalAppInfo_ReturnListOfExternalAppInfo() {
        every { getExternalAppObservableUseCase.invoke() } returns Single.just(
            listOf(
                mockExternalAppInfo
            )
        )
        launchExternalAppViewModel = ExternalAppListViewModel(
            getExternalAppObservableUseCase,
            getLaunchIntentForPackageUseCase
        )

        val result = launchExternalAppViewModel.apps.value

        Assert.assertEquals(
            listOf(mockExternalAppInfo),
            result
        )
    }

    @Test
    fun getApps_OnErrorLoadData_UpdateAny() {
        every { getExternalAppObservableUseCase.invoke() } returns Single.error(Throwable())

        launchExternalAppViewModel = ExternalAppListViewModel(
            getExternalAppObservableUseCase,
            getLaunchIntentForPackageUseCase
        )

        val result = launchExternalAppViewModel.apps.value

        Assert.assertEquals(
            listOf<ExternalAppInfo>(),
            result
        )
    }


    @Test
    fun getIntentForExecution_ExecutableAndExistLaunchIntent_InvokeStartActivity() {
        every { mockExternalAppInfo.executable } returns true
        every { mockStartActivityLambda.invoke(any()) } just runs
        every { getLaunchIntentForPackageUseCase.invoke(mockExternalAppInfo) } returns mockAppLaunchIntent
        every { getExternalAppObservableUseCase.invoke() } returns Single.just(listOf())

        launchExternalAppViewModel = ExternalAppListViewModel(
            getExternalAppObservableUseCase,
            getLaunchIntentForPackageUseCase
        )

        launchExternalAppViewModel.getIntentForExecution(
            mockExternalAppInfo,
            mockStartActivityLambda
        )

        verify { mockStartActivityLambda(mockAppLaunchIntent) }
    }

    @Test
    fun getIntentForExecution_ExecutableAndNotExistLaunchIntent_NotInvokeStartActivity() {
        every { mockExternalAppInfo.executable } returns true
        every { getLaunchIntentForPackageUseCase.invoke(mockExternalAppInfo) } returns null
        every { getExternalAppObservableUseCase.invoke() } returns Single.just(listOf())

        launchExternalAppViewModel = ExternalAppListViewModel(
            getExternalAppObservableUseCase,
            getLaunchIntentForPackageUseCase
        )

        launchExternalAppViewModel.getIntentForExecution(
            mockExternalAppInfo,
            mockStartActivityLambda
        )

        verify(exactly = 0) { mockStartActivityLambda(mockAppLaunchIntent) }
    }

    @Test
    fun getIntentForExecution_NotExecutable_InvokeStartActivityWithMarketIntent() {
        every { mockExternalAppInfo.executable } returns false
        every { mockExternalAppInfo.marketUri } returns "TestUri"
        every { mockStartActivityLambda.invoke(any()) } just runs
        every { getExternalAppObservableUseCase.invoke() } returns Single.just(listOf())

        launchExternalAppViewModel = ExternalAppListViewModel(
            getExternalAppObservableUseCase,
            getLaunchIntentForPackageUseCase
        )

        launchExternalAppViewModel.getIntentForExecution(
            mockExternalAppInfo,
            mockStartActivityLambda
        )

        verify { mockStartActivityLambda(any()) }
    }
}