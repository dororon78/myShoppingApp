package com.example.myshoppingapp.domain.usecases

import com.example.myshoppingapp.domain.ExternalAppInfo
import com.example.myshoppingapp.domain.repository.ExternalAppRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test

internal class GetExternalAppObservableUseCaseTest {

    private lateinit var getExternalAppObservableUseCase: GetExternalAppObservableUseCase

    private val mockExternalAppRepository: ExternalAppRepository = mockk()
    private val mockSingle: Single<List<ExternalAppInfo>> = mockk()

    @Before
    fun setUp() {
        getExternalAppObservableUseCase = GetExternalAppObservableUseCase(mockExternalAppRepository)
    }

    @Test
    fun invoke_InvokeRepositoryWithSameValue() {
        every { mockExternalAppRepository.getExternalAppObservable() } returns mockSingle

        getExternalAppObservableUseCase.invoke()

        verify { mockExternalAppRepository.getExternalAppObservable() }
    }
}