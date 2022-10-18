package simple.program.domain.repositories

import kotlinx.coroutines.flow.Flow
import simple.program.domain.model.Contact

interface ContactRepository {
    suspend fun deleteContact(contact: Contact)

    suspend fun saveContact(contact: Contact)

    fun getAllContact(): Flow<List<Contact>>
}