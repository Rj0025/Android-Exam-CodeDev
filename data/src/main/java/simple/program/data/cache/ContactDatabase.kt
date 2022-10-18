package simple.program.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import simple.program.data.model.ContactEntity
import javax.inject.Inject
import javax.inject.Provider


@Database(entities = [ContactEntity::class], version = 1)
abstract class ContactDatabase: RoomDatabase() {
    abstract fun contactDao(): ContactDao

    class Callback@Inject constructor(
        private val database: Provider<ContactDatabase>,
        private val applicationScope: CoroutineScope
    ) :RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val dao = database.get().contactDao()

            applicationScope.launch {
                dao.insert(ContactEntity("Test","Data 1","11111111", false))
                dao.insert(ContactEntity("Test","Data 2","2222222222", true))
                dao.insert(ContactEntity("Test","Data 3","3333333333", false))
            }
        }
    }
}