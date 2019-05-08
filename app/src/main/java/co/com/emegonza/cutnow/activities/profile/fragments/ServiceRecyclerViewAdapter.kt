package co.com.emegonza.cutnow.activities.profile.fragments

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.com.emegonza.cutnow.R
import co.com.emegonza.cutnow.model.ServiceBarber
import kotlinx.android.synthetic.main.item_service.view.*

class ServiceRecyclerViewAdapter(val items : ArrayList<ServiceBarber>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ServiceRecyclerViewHolderItem {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_service, parent, false)
        return ServiceRecyclerViewHolderItem(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // Populate ViewHolder with data that corresponds to the position in the list
        // which we are told to load
        (holder as ServiceRecyclerViewHolderItem).bind(items[position])
    }

    //ViewHolder
    class ServiceRecyclerViewHolderItem(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(service: ServiceBarber) {
            itemView.serviceName.text = service.serviceName
            itemView.valueService.text = service.price
            //itemView.setOnClickListener { clickListener(user) }
        }
    }
}