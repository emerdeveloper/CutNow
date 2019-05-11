package co.com.emegonza.cutnow.activities.profile.fragments

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.com.emegonza.cutnow.R
import co.com.emegonza.cutnow.model.UserReview
import kotlinx.android.synthetic.main.review.view.*

class ReviewRecyclerViewAdapter(val items : ArrayList<UserReview>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ReviewRecyclerViewHolderItem {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.review, parent, false)
        return ReviewRecyclerViewHolderItem(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // Populate ViewHolder with data that corresponds to the position in the list
        // which we are told to load
        (holder as ReviewRecyclerViewHolderItem).bind(items[position])
    }

    //ViewHolder
    class ReviewRecyclerViewHolderItem(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(userReview: UserReview) {
            //Picasso.get().load(service.profileImage).into(itemView.userProfileImage)
            itemView.usersName.text = userReview.name
            itemView.userComment.text = userReview.comment
            itemView.dateReview.text = userReview.dateReview
        }
    }
}