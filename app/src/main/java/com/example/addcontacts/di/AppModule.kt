package com.example.addcontacts.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.addcontacts.data.local.PhoneContactsDatabase
import com.example.addcontacts.data.local.dao.PhoneDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PhoneContactsDatabase{
        return Room.databaseBuilder(
            context,
            PhoneContactsDatabase::class.java,
            "phone_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(database: PhoneContactsDatabase): PhoneDao{
        return database.phone_dao()
    }

}