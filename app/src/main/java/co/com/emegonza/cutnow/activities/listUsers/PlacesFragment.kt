package co.com.emegonza.cutnow.activities.listUsers

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import co.com.emegonza.cutnow.R
import co.com.emegonza.cutnow.activities.profile.BarberProfileFragment
import co.com.emegonza.cutnow.model.User

class PlacesFragment : Fragment() {
    private lateinit var adapter: UserRecyclerViewAdapter
    val users: ArrayList<User> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_place_list, container, false)
        val recyclerView : RecyclerView = view.findViewById(R.id.places_recycler_view)

        addUsers()

        //linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager =  LinearLayoutManager(activity)
        recyclerView.hasFixedSize()
        recyclerView.adapter = UserRecyclerViewAdapter(users, { userItem : User -> barberItemClicked(userItem) })
        return view
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
        //listener = null
    }

    private fun barberItemClicked(userItem : User) {
        Toast.makeText(activity, "Clicked: ${userItem.name}", Toast.LENGTH_LONG).show()

        val bundle: Bundle = Bundle()
        val fragment: BarberProfileFragment = BarberProfileFragment()
        bundle.putString("name", userItem.name)

        fragment.arguments = bundle
        activity?.supportFragmentManager!!
            .beginTransaction()
            //.AddSharedElement(image, image.TransitionName)
            .replace(R.id.container_frame, fragment)
            .addToBackStack("barber")
            .commit();
    }

    private fun addUsers()
    {
        for (i in 1..15) {
            users.add(User("Emerson 00000"+ i, "Bello", 4.5, 100))
        }
    }

}
