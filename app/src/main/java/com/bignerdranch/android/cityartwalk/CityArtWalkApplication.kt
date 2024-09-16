package com.bignerdranch.android.cityartwalk

import android.app.Application

class CityArtWalkApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ArtRepository.initialize(this)
    }
}