package simple.program.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import simple.program.domain.model.Contact


@Entity(tableName = "contact_table")
data class ContactEntity(
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val favorites: Boolean,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
) {
    fun toDomainModel() =
        Contact(
            firstName = firstName,
            lastName = lastName,
            phoneNumber = phoneNumber,
            favorites = favorites,
            id = id
        )
}
