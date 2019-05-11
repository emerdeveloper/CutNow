package co.com.emegonza.cutnow.activities.appointment

import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import co.com.emegonza.cutnow.MainActivity
import co.com.emegonza.cutnow.R
import co.com.emegonza.cutnow.model.ServiceBarber
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.appointment.view.*
import android.support.design.widget.BottomSheetBehavior
import android.widget.TextView
import kotlinx.android.synthetic.main.appointment_confirm.view.*

class AppointmentFragment : Fragment() {

    private var mainActivity: MainActivity = MainActivity()
    lateinit var name: String
    lateinit var imageProfile: String
    var totalPayment: Double = 0.0
    private lateinit var sheetBehavior: BottomSheetBehavior<LinearLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle: Bundle? = arguments
        if (bundle != null)
            name = bundle.getString("name")
            imageProfile = bundle!!.getString("imageProfile")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.appointment, container, false)

        sheetBehavior = BottomSheetBehavior.from<LinearLayout>(view.appointment_confirm)
        //view.showDetail.paintFlags = view.showDetail.paintFlags or Paint.UNDERLINE_TEXT_FLAG;

        var containerButtons = activity!!.findViewById<LinearLayout>(R.id.container_visualization)
        containerButtons.visibility = View.GONE;

        Picasso.get().load(imageProfile).into(view.profileImage)
        view.nameBarber.text = name
        view.recyclerServices.layoutManager =  LinearLayoutManager(mainActivity)
        view.recyclerServices.hasFixedSize()
        view.recyclerServices.adapter = ServiceBarberRecyclerViewAdapter(addServiceBarber(), { userItem : ServiceBarber -> serviceItemClicked(userItem) })

        return view
    }


    private fun serviceItemClicked(userItem : ServiceBarber) {
        //Toast.makeText(activity, "Clicked: ${userItem.price}", Toast.LENGTH_LONG).show()
        getTotalPayment(userItem.price)
        showBottomSheet()
    }

    private fun addServiceBarber() : ArrayList<ServiceBarber>
    {
        var serviceBarber : ArrayList<ServiceBarber> = ArrayList()
        for (i in 1..3) {
            serviceBarber.add(ServiceBarber("Haircut" + i,  "$ " + (i+1) + "2.0000", 45))
        }
        return serviceBarber;
    }

    private fun showBottomSheet() {
        if (sheetBehavior!!.state == BottomSheetBehavior.STATE_HIDDEN) {
            sheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
        } else if (sheetBehavior!!.state == BottomSheetBehavior.STATE_EXPANDED){
            sheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
        } else if (sheetBehavior!!.state == BottomSheetBehavior.STATE_HALF_EXPANDED){
            sheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    /**
     * bottom sheet state change listener
     * we are changing button text when sheet changed state
     * */
    private fun behaviorBottomSheet()  {
        sheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    /*BottomSheetBehavior.STATE_HIDDEN -> {
                    }
                    BottomSheetBehavior.STATE_EXPANDED ->
                        btBottomSheet.text = "Close Bottom Sheet"
                    BottomSheetBehavior.STATE_COLLAPSED ->
                        btBottomSheet.text = "Expand Bottom Sheet"
                    BottomSheetBehavior.STATE_DRAGGING -> {
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                    }*/
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })
    }

    private fun getTotalPayment(valueString: String) {
        val value = valueString.replace("$","")
        val valuePayment = value.toDouble()
        totalPayment = totalPayment + valuePayment
        view!!.totalPayment.text = "$" + totalPayment
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
        //listener = null
    }
}