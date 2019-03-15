package co.com.emegonza.cutnow

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.Button
import co.com.emegonza.cutnow.activities.listUsers.PlacesFragment
import co.com.emegonza.cutnow.activities.map.MapsFragment


class MainActivity : AppCompatActivity() {

    private var place : Button? = null
    private var map : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        setUpEvents()

        addFragment(MapsFragment(), false, "one")
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
        val manager = supportFragmentManager
        val ft = manager.beginTransaction()

        if (addToBackStack) {
            ft.addToBackStack(tag)
        }
        ft.replace(R.id.container_frame, fragment, tag)
        ft.commitAllowingStateLoss()
    }
}
