package simple.program.myapplication.presentation.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import simple.program.myapplication.databinding.ItemContactBinding
import simple.program.domain.model.Contact

class ContactAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var viewBindings: ItemContactBinding

    private val diffUtil = object : DiffUtil.ItemCallback<Contact>() {

        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Contact,
            newItem: Contact,
        ): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        viewBindings = ItemContactBinding
            .inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return FavoritesViewHolder(viewBindings)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FavoritesViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Contact>) {
        differ.submitList(list)
    }

    @SuppressLint("SetTextI18n")
    inner class FavoritesViewHolder
    constructor(
        private val viewBindings: ItemContactBinding,
    ) : RecyclerView.ViewHolder(viewBindings.root) {

        init {
            viewBindings.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        interaction?.onItemSelected(differ.currentList[position])
                    }
                }
                ckbFavorites.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        interaction?.onCheckBoxClick(differ.currentList[position],
                            ckbFavorites.isChecked)
                    }
                }
                btnDelete.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        interaction?.onItemDeleted(differ.currentList[position])
                    }
                }
            }
        }

        fun bind(item: Contact) = with(viewBindings) {
            txtName.text = "${item.firstName} ${item.lastName}"
            txtPhoneNumber.text = item.phoneNumber
            ckbFavorites.isChecked = item.favorites
        }
    }

    interface Interaction {
        fun onItemSelected(item: Contact)
        fun onItemDeleted(item: Contact)
        fun onCheckBoxClick(item: Contact, isChecked: Boolean)
    }
}

