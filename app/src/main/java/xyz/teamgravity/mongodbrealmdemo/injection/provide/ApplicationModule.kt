package xyz.teamgravity.mongodbrealmdemo.injection.provide

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.teamgravity.mongodbrealmdemo.data.local.school.database.SchoolDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideSchoolDatabase(): SchoolDatabase = SchoolDatabase()
}