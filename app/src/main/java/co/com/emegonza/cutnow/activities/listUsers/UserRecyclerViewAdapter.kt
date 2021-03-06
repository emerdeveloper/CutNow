package co.com.emegonza.cutnow.activities.listUsers

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.com.emegonza.cutnow.R
import co.com.emegonza.cutnow.model.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list.view.*

class UserRecyclerViewAdapter(val items : ArrayList<User>, val clickListener: (User) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): UserRecyclerViewHolderItem {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_list, parent, false)
        return UserRecyclerViewHolderItem(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // Populate ViewHolder with data that corresponds to the position in the list
        // which we are told to load
        (holder as UserRecyclerViewHolderItem).bind(items[position], clickListener)
    }

    //ViewHolder
    class UserRecyclerViewHolderItem(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(user: User, clickListener: (User) -> Unit) {
            itemView.nameTextView.text = user.name
            Picasso.get().load(user.profileImage).into(itemView.profileImage)
            itemView.location.text = user.location
            itemView.qualification.text = user.qualification.toString()
            itemView.usersQuantity.text = "(" + user.usersQuantity.toString() + ")"
            itemView.setOnClickListener { clickListener(user) }
        }
    }
}
