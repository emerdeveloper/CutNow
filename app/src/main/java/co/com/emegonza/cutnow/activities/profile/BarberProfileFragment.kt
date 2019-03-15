package co.com.emegonza.cutnow.activities.profile


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.support.v7.widget.Toolbar;
import co.com.emegonza.cutnow.R

class BarberProfileFragment : Fragment() {

    lateinit var name: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle: Bundle? = arguments
        if (bundle != null)
            name = bundle.getString("name")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.barber_profile, container, false)
        val toolbarLayout : Toolbar = view.findViewById(R.id.toolbar)


        if(activity is AppCompatActivity){
            (activity as AppCompatActivity).setSupportActionBar(toolbarLayout)
            if ((activity as AppCompatActivity).supportActionBar != null)
                (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        toolbarLayout.title = name
        /*fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/

        //linearLayoutManager = LinearLayoutManager(activity)
        /*recyclerView.layoutManager =  LinearLayoutManager(activity)
        recyclerView.hasFixedSize()
        recyclerView.adapter = UserRecyclerViewAdapter(users, { userItem : User -> barberItemClicked(userItem) })*/
        return view
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
        //listener = null
    }
}
