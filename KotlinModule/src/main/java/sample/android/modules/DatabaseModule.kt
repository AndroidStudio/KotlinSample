package sample.android.modules

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import sample.android.local.Database
import javax.inject.Singleton

@Module
class DatabaseModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideDatabase(): Database {
        return Room.databaseBuilder(context, Database::class.java, "database")
                .fallbackToDestructiveMigration()
                .build();
    }

}
