package co.com.emegonza.cutnow.activities.listUsers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import co.com.emegonza.cutnow.R
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
        recyclerView.adapter = UserRecyclerViewAdapter(users, { userItem : User -> partItemClicked(userItem) })
        return view
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
        //listener = null
    }

    private fun partItemClicked(userItem : User) {
        Toast.makeText(activity, "Clicked: ${userItem.name}", Toast.LENGTH_LONG).show()

        // Launch second activity, pass part ID as string parameter
        /*val showDetailActivityIntent = Intent(activity, PartDetailActivity::class.java)
        showDetailActivityIntent.putExtra(Intent.EXTRA_TEXT, partItem.id.toString())
        startActivity(showDetailActivityIntent)*/
    }

    private fun addUsers()
    {
        for (i in 1..15) {
            users.add(User("Emerson 00000"+ i, "Bello", 4.5, 100))
        }
    }

}
