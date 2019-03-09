package co.com.emegonza.cutnow

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import android.R


class MapsActivity : AppCompatActivity(),
    OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener {

    private val MY_LOCATION_REQUEST_CODE = 1
    private lateinit var mMap: GoogleMap
    var place : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        init()
        setUpEvents()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    //Init Widgets
    fun init() {
        place = findViewById(R.id.btn_places)
    }

    //Setup Events
    fun setUpEvents() {
        place?.setOnClickListener { showPlacesList() }
    }

    private fun showPlacesList() {

    }


    private fun setFragment() {
        val fragmentManager: FragmentManager
        val fragmentTransaction: FragmentTransaction

        fragmentManager = supportFragmentManager
        fragmentTransaction = fragmentManager.beginTransaction()
        val inboxFragment = PlacesFragment()
        fragmentTransaction.replace(R.id.map, inboxFragment)
        fragmentTransaction.commit()
    }




    //Events Maps
    fun enableMyLocationIfPermitted() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
                MY_LOCATION_REQUEST_CODE
            )

            // Show rationale and request permission.
            Toast.makeText(
                this, "Location permission not granted, " + "showing default location",
                Toast.LENGTH_SHORT
            ).show()

        } else {

            mMap.isMyLocationEnabled = true
            mMap.setOnMyLocationButtonClickListener(this)
            mMap.setOnMyLocationClickListener(this)
        }
    }

    fun showDefaultLocation() {
        Toast.makeText(
            this, "Location permission not granted, " + "showing default location",
            Toast.LENGTH_SHORT
        ).show()
        val redmond = LatLng(47.6739881, -122.121512)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(redmond))
    }


    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        enableMyLocationIfPermitted()

        // Enable Zoom
        mMap.uiSettings.isZoomGesturesEnabled = true
        //mMap.setMinZoomPreference(11f)
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode != MY_LOCATION_REQUEST_CODE) {
            return
        }

        if (permissions.size > 0 &&

            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            enableMyLocationIfPermitted()

        } else {
            // Permission was denied. Display an error message.
            showDefaultLocation()
        }
    }

    override fun onMyLocationButtonClick(): Boolean {
        //mMap.setMinZoomPreference(15f);
        return false;
    }

    override fun onMyLocationClick(location: Location) {
        //mMap.setMinZoomPreference(12f)

        val circleOptions = CircleOptions()
        circleOptions.center(
            LatLng(
                location.latitude,
                location.longitude
            )
        )

        circleOptions.radius(200.0)
        circleOptions.fillColor(Color.RED)
        circleOptions.strokeWidth(6f)

        mMap.addCircle(circleOptions)
    }

    //Google playServices
    private fun isGooglePlayServicesAvailable(): Boolean {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val status = googleApiAvailability.isGooglePlayServicesAvailable(this)
        if (ConnectionResult.SUCCESS == status)
            return true
        else {
            if (googleApiAvailability.isUserResolvableError(status))
                Toast.makeText(
                    this,
                    "Please Install google play services to use this application",
                    Toast.LENGTH_LONG
                ).show()
        }
        return false
    }
}
