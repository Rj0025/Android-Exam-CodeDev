package simple.program.data.cache

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import simple.program.data.model.ContactEntity

@Dao
interface ContactDao {

    @Query("Select * FROM contact_table")
    fun getContact(): Flow<List<ContactEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contactEntity: ContactEntity)

    @Update
    suspend fun update(contactEntity: ContactEntity)

    @Delete
    suspend fun delete(contactEntity: ContactEntity)
}