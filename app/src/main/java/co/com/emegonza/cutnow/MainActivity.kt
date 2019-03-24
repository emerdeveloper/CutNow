package co.com.emegonza.cutnow

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.Button
import co.com.emegonza.cutnow.activities.listUsers.PlacesFragment
import co.com.emegonza.cutnow.activities.map.MapsFragment
import co.com.emegonza.cutnow.contracts.FirebaseDelegate
import com.google.firebase.database.*
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser


class MainActivity : AppCompatActivity() {

    private var place : Button? = null
    private var map : Button? = null
    lateinit var database: DatabaseReference
    var delegate: FirebaseDelegate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        setUpEvents()

        addFragment(MapsFragment(), false, "one")

        //Get Instance database
        database = FirebaseDatabase.getInstance().reference

        getPlaces()
    }

    //Init Widgets
    private fun init() {
        place = findViewById(R.id.btn_places)
        map = findViewById(R.id.btn_map)
    }

    //Setup Events
    private fun setUpEvents() {
        place?.setOnClickListener { showPlacesList() }
        map?.setOnClickListener { showMap() }
    }

    private fun showPlacesList() {
        addFragment(PlacesFragment(), false, "places")
    }

    private fun showMap() {
        addFragment(MapsFragment(), false, "map")
    }

    //Methods
    private fun addFragment(fragment: Fragment, addToBackStack: Boolean, tag: String) {

        if (fragment is FirebaseDelegate) {
            delegate = fragment
        }

        val manager = supportFragmentManager
        val ft = manager.beginTransaction()

        if (addToBackStack) {
            ft.addToBackStack(tag)
        }
        ft.replace(R.id.container_frame, fragment, tag)
        ft.commitAllowingStateLoss()
    }

    //Get Data from Firebase
    private fun getPlaces(){
        var jsonObject = JSONObject()
        val menuListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                var value =  dataSnapshot.value as (Map<String, String>)
                jsonObject = JSONObject(value)
                delegate?.OnUpdateBarberData(jsonObject)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        database.child("barber").addValueEventListener(menuListener)
    }
}
