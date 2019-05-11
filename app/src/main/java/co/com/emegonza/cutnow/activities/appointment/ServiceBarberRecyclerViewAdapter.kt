package co.com.emegonza.cutnow.activities.appointment

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.com.emegonza.cutnow.R
import co.com.emegonza.cutnow.model.ServiceBarber
import kotlinx.android.synthetic.main.item_service_select.view.*

class ServiceBarberRecyclerViewAdapter(val items : ArrayList<ServiceBarber>, val clickListener: (ServiceBarber) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ServiceBarberRecyclerViewHolderItem {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_service_select, parent, false)
        return ServiceBarberRecyclerViewHolderItem(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // Populate ViewHolder with data that corresponds to the position in the list
        // which we are told to load
        (holder as ServiceBarberRecyclerViewHolderItem).bind(items[position], clickListener)
    }

    //ViewHolder
    class ServiceBarberRecyclerViewHolderItem(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(service: ServiceBarber, clickListener: (ServiceBarber) -> Unit) {
            itemView.serviceName.text = service.serviceName
            itemView.serviceDuration.text = "" + service.duration + " minutos"
            itemView.valueService.text = service.price
            itemView.setOnClickListener {
                itemView.checkboxService.isChecked =  if (itemView.checkboxService.isChecked) false else true
                clickListener(service)
            }
        }
    }
}
