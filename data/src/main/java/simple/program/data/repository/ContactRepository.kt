package simple.program.data.repository

import kotlinx.coroutines.flow.map
import simple.program.data.cache.ContactDao
import simple.program.data.util.toEntityModel
import simple.program.domain.model.Contact
import simple.program.domain.repositories.ContactRepository
import javax.inject.Inject

class ContactRepositoryImpl @Inject constructor(
    private val dao: ContactDao,
): ContactRepository {
    override suspend fun deleteContact(contact: Contact) {
        dao.delete(contact.toEntityModel())
    }

    override suspend fun saveContact(contact: Contact) {
        dao.insert(contact.toEntityModel())
    }

    override fun getAllContact() = dao.getContact().map { list ->
        list.map { it.toDomainModel() }
    }
}