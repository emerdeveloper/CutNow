package co.com.emegonza.cutnow.activities.profile.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import co.com.emegonza.cutnow.MainActivity
import co.com.emegonza.cutnow.R
import co.com.emegonza.cutnow.model.ServiceBarber

class ServiceFragment : Fragment() {

    private var mainActivity: MainActivity = MainActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*val bundle: Bundle? = arguments
        if (bundle != null){}
            name = bundle.getString("name")
        imageProfile = bundle!!.getString("imageProfile")*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.services, container, false)
        val serviceRecyclerView : RecyclerView = view.findViewById(R.id.service_recycler_view)

        /*if(activity is AppCompatActivity){
            (activity as AppCompatActivity).setSupportActionBar(toolbarLayout)
            if ((activity as AppCompatActivity).supportActionBar != null)
                (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }*/

        var containerButtons = activity!!.findViewById<LinearLayout>(R.id.container_visualization)
        containerButtons.visibility = View.GONE;


        mainActivity =  activity as MainActivity

        serviceRecyclerView.layoutManager =  GridLayoutManager(mainActivity, 2)
        //serviceRecyclerView.addItemDecoration(MarginDecoration(20, 2))
        serviceRecyclerView.hasFixedSize()
        serviceRecyclerView.adapter = ServiceRecyclerViewAdapter(addServiceBarber())

        return view
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
        //listener = null
    }

    private fun addServiceBarber() : ArrayList<ServiceBarber>
    {
        var serviceBarber : ArrayList<ServiceBarber> = ArrayList()
        for (i in 1..7) {
            serviceBarber.add(ServiceBarber("Corte de cabello" + i,  "$ " + (i+1) + "2.0000", 45))
        }
        return serviceBarber;
    }
}