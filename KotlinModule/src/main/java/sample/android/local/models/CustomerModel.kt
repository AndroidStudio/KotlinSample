package sample.android.local.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import sample.android.local.tables.Tables.Companion.CUSTOMER

@Entity(tableName = CUSTOMER)
class CustomerModel {

    @ColumnInfo(name = "id")
    @PrimaryKey()
    var id: Int = 0

    @ColumnInfo(name = "name")
    var name: String? = null

    @ColumnInfo(name = "email")
    var email: String? = null
}