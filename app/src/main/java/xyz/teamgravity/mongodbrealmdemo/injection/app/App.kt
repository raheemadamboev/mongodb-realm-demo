package xyz.teamgravity.mongodbrealmdemo.injection.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import xyz.teamgravity.mongodbrealmdemo.data.local.school.database.SchoolDatabase
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var database: SchoolDatabase

    override fun onCreate() {
        super.onCreate()

        database.initialize()
    }
}