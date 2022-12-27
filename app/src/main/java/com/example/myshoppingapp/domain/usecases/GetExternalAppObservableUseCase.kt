package com.example.myshoppingapp.domain.usecases

import com.example.myshoppingapp.domain.ExternalAppInfo
import com.example.myshoppingapp.domain.repository.ExternalAppRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetExternalAppObservableUseCase @Inject internal constructor(
    private val repository: ExternalAppRepository,
) {
    operator fun invoke(): Single<List<ExternalAppInfo>> {
        return repository.getExternalAppObservable()
    }
}