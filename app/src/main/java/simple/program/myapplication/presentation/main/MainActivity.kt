package simple.program.myapplication.presentation.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import simple.program.myapplication.databinding.ActivityMainBinding
import simple.program.domain.model.Contact
import simple.program.myapplication.presentation.MainViewModel
import simple.program.myapplication.presentation.addEdit.AddEditActivity

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    lateinit var contactAdapter: ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.contact.observe(this) {
            contactAdapter.submitList(it)
        }
    }

    private fun setupView() = binding.apply {
        contactAdapter = ContactAdapter(object : ContactAdapter.Interaction {
            override fun onItemSelected(item: Contact) {
                val intent = Intent(this@MainActivity, AddEditActivity::class.java)
                intent.putExtra(AddEditActivity.EXTRA_CONTACT, item)
                startActivity(intent)
            }

            override fun onItemDeleted(item: Contact) {
                viewModel.deleteContact(item)
            }

            override fun onCheckBoxClick(item: Contact, isChecked: Boolean) {
                viewModel.setFavorites(item, isChecked)
            }
        })

        rvContact.adapter = contactAdapter
        fabAdd.setOnClickListener {
            val intent = Intent(this@MainActivity, AddEditActivity::class.java)
            startActivity(intent)
        }
    }
}