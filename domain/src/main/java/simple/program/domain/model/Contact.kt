package simple.program.domain.model

import java.io.Serializable

data class Contact(
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val favorites: Boolean,
    val id: Int? = null
): Serializable {}
