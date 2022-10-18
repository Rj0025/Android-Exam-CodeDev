package simple.program.myapplication.presentation.addEdit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import simple.program.myapplication.R
import simple.program.myapplication.databinding.ActivityAddEditBinding
import simple.program.domain.model.Contact
import simple.program.myapplication.presentation.MainViewModel

@AndroidEntryPoint
class AddEditActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_CONTACT = "EXTRA_CONTACT"
    }

    var contact: Contact? = null

    lateinit var binding: ActivityAddEditBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        contact = intent.getSerializableExtra(EXTRA_CONTACT) as? Contact

        setupView()
        setupObserver()
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.event.collect {
                    Toast.makeText(this@AddEditActivity, getString(it), Toast.LENGTH_SHORT).show()
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.navigate.collect {
                    if (it) finish()
                }
            }
        }
    }

    private fun setupView() = binding.apply {
        if (contact != null) {
            supportActionBar?.title = getString(R.string.edit)
            setupContactDetails()
        } else {
            supportActionBar?.title = getString(R.string.new_contact)
        }

        btnSave.setOnClickListener {
            viewModel.saveContact(
                edtFirstname.text.toString(),
                edtLastname.text.toString(),
                edtPhoneNumber.text.toString(),
                ckbFavorites.isChecked,
                contact?.id
            )
        }
    }

    private fun setupContactDetails() = binding.apply {
        edtFirstname.setText(contact?.firstName)
        edtLastname.setText(contact?.lastName)
        edtPhoneNumber.setText(contact?.phoneNumber)
        ckbFavorites.isChecked = contact?.favorites ?: false
    }
}