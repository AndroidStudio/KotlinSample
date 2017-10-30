package sample.android.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import sample.android.local.interfaces.DatabaseQuery
import sample.android.local.models.CustomerModel

@Database(entities = arrayOf(CustomerModel::class), version = 4)
abstract class Database : RoomDatabase() {

    abstract fun query(): DatabaseQuery

}