package co.com.emegonza.cutnow.activities.profile.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import co.com.emegonza.cutnow.MainActivity
import co.com.emegonza.cutnow.R
import co.com.emegonza.cutnow.model.ServiceBarber
import co.com.emegonza.cutnow.model.UserReview

class ReviewFragment : Fragment() {

    private var mainActivity: MainActivity = MainActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.services, container, false)
        val serviceRecyclerView : RecyclerView = view.findViewById(R.id.service_recycler_view)

        var containerButtons = activity!!.findViewById<LinearLayout>(R.id.container_visualization)
        containerButtons.visibility = View.GONE;


        mainActivity =  activity as MainActivity

        serviceRecyclerView.layoutManager =  LinearLayoutManager(mainActivity)
        serviceRecyclerView.hasFixedSize()
        serviceRecyclerView.adapter = ReviewRecyclerViewAdapter(addUserReview())

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
        //listener = null
    }

    private fun addUserReview() : ArrayList<UserReview>
    {
        var userReview : ArrayList<UserReview> = ArrayList()
        for (i in 1..15) {
            userReview.add(UserReview("Homero J. Simpson", "26/04/2017", "Nunca debe hacer lo contrario a lo que quiera el cliente por más que considere que tiene la razón. Lo fundamental es siempre preguntarle qué quiere hacer con su barba y orientarlo en el proceso sin imponer nada.", ""));
        }
        return userReview;
    }
}