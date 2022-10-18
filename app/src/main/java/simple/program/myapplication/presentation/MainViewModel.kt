package simple.program.myapplication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import simple.program.myapplication.R
import simple.program.data.repository.ContactRepositoryImpl
import simple.program.domain.model.Contact
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ContactRepositoryImpl,
) : ViewModel() {

    private val _error = Channel<Int>()
    val event = _error.receiveAsFlow()

    private val _navigate = Channel<Boolean>()
    val navigate = _navigate.receiveAsFlow()

    fun saveContact(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        isFavorite: Boolean,
        id: Int?,
    ) = viewModelScope.launch {

        if (firstName.isEmpty()){
            _error.send(R.string.first_name_required)
            return@launch
        }
        if (lastName.isEmpty()){
            _error.send(R.string.last_name_required)
            return@launch
        }
        if (phoneNumber.isEmpty()){
            _error.send(R.string.phone_number_required)
            return@launch
        }

        repository.saveContact(
            Contact(
                firstName,
                lastName,
                phoneNumber,
                isFavorite,
                id
            )
        )
        _navigate.send(true)
    }

    fun setFavorites(contact: Contact, isChecked: Boolean) = viewModelScope.launch {
        val updatedContact = contact.copy(favorites = isChecked)
        repository.saveContact(updatedContact)
    }

    fun deleteContact(contact: Contact) = viewModelScope.launch {
        repository.deleteContact(contact)
    }
    val contact = repository.getAllContact().asLiveData()
}