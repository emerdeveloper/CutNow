package co.com.emegonza.cutnow.activities.listUsers

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import co.com.emegonza.cutnow.MainActivity
import co.com.emegonza.cutnow.R
import co.com.emegonza.cutnow.activities.profile.BarberProfileFragment
import co.com.emegonza.cutnow.contracts.FirebaseDelegate
import co.com.emegonza.cutnow.model.User
import org.json.simple.JSONObject

class PlacesFragment : Fragment(), FirebaseDelegate {

    private var mainActivity: MainActivity = MainActivity()

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

        mainActivity =  activity as MainActivity

        recyclerView.layoutManager =  LinearLayoutManager(mainActivity)
        recyclerView.hasFixedSize()
        recyclerView.adapter = UserRecyclerViewAdapter(addUsers(), { userItem : User -> barberItemClicked(userItem) })
        return view
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onResume() {
        super.onResume()
        var containerButtons = mainActivity.findViewById<LinearLayout>(R.id.container_visualization)
        containerButtons.visibility = View.VISIBLE;
    }

    private fun barberItemClicked(userItem : User) {
        //Toast.makeText(mainActivity, "Clicked: ${userItem.name}", Toast.LENGTH_LONG).show()

        val bundle: Bundle = Bundle()
        val fragment: BarberProfileFragment = BarberProfileFragment()
        bundle.putString("name", userItem.name)
        bundle.putString("imageProfile", userItem.profileImage)

        fragment.arguments = bundle
        mainActivity?.supportFragmentManager!!
            .beginTransaction()
            //.addSharedElement(sharedElement, transitionName)
            .replace(R.id.container_frame, fragment)
            .addToBackStack("barber")
            .commit()
    }

    private fun addUsers() : ArrayList<User>
    {
        val users: ArrayList<User> = ArrayList()
        for (item in mainActivity.data!!)
        {
            var name = (item.value as JSONObject).get("name") as String
            var location = ((item.value as JSONObject).get("location") as JSONObject).get("city") as String
            var qualificationAny = (item.value as JSONObject).get("qualification")
            var qualification = if (qualificationAny is Long) qualificationAny.toDouble() else qualificationAny as Double
            var profileImage = (item.value as JSONObject).get("profileImage") as String
            users.add(User( name, location, qualification, 100, profileImage))
        }
        return users
    }

    override fun onUpdateBarberData(barber: JSONObject) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
