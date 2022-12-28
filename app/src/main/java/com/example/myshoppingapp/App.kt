package com.example.myshoppingapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// TODO @ViewModelScoped 지정하기
@HiltAndroidApp
class App : Application()