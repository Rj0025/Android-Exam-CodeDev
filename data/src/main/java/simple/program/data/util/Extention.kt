package simple.program.data.util

import simple.program.data.model.ContactEntity
import simple.program.domain.model.Contact


fun Contact.toEntityModel(): ContactEntity {
    return ContactEntity(
        firstName = this.firstName,
        lastName = this.lastName,
        phoneNumber = this.phoneNumber,
        favorites = this.favorites,
        id = this.id ?: 0
    )
}