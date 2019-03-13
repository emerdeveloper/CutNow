package co.com.emegonza.cutnow

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
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
import com.google.android.gms.common.GooglePlayServicesUtil

class MapsActivity : AppCompatActivity(),
    OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener,
    LocationListener {

    private val MY_LOCATION_REQUEST_CODE = 1
    private lateinit var mMap: GoogleMap
    var place : Button? = null
    var fragment : SupportMapFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        init()
        setUpEvents()

        if (isGooglePlayServicesAvailable())
        fragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        fragment?.getMapAsync(this)
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
        setFragment(PlacesFragment())
    }


    private fun setFragment(fragment : Fragment) {
        val fragmentManager: FragmentManager
        val fragmentTransaction: FragmentTransaction

        fragmentManager = supportFragmentManager
        fragmentTransaction = fragmentManager.beginTransaction()
        //val inboxFragment = PlacesFragment()
        fragmentTransaction.replace(R.id.map,fragment)
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

            var locationManager : LocationManager =  getSystemService(LOCATION_SERVICE) as LocationManager
            var bestProvider : String = locationManager.getBestProvider(Criteria(), true)
            var location = locationManager.getLastKnownLocation(bestProvider);
            if (location != null) {
                onLocationChanged(location);
            }
            locationManager.requestLocationUpdates(bestProvider, 20000, 0f, this)
            //locationManager.requestLocationUpdates(bestProvider, 20000, 0, this)
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
        //mMap.setMinZoomPreference(15f)

        mMap.addCircle(createCircle(location))
    }

    //Create Circle
    fun createCircle(location: Location) : CircleOptions {
        val circleOptions = CircleOptions()
        circleOptions.center(
            LatLng(
                location.latitude,
                location.longitude
            )
        )

        circleOptions.radius(200.0)
        //circleOptions.fillColor(R.color.colorCircle)
        circleOptions.strokeWidth(6f)
        return circleOptions
    }

    //Get Location FirstTime
    override fun onLocationChanged(location: Location?) {
        var latLng =  LatLng(location!!.latitude, location!!.longitude)
        mMap.addCircle(createCircle(location!!))
        //mMap.addMarker(new MarkerOptions().position(latLng));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15f));
    }

    override fun onProviderEnabled(provider: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderDisabled(provider: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



    //Google playServices
    private fun isGooglePlayServicesAvailable(): Boolean {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val status = googleApiAvailability.isGooglePlayServicesAvailable(this)
        if (ConnectionResult.SUCCESS == status)
            return true
        else  if (googleApiAvailability.isUserResolvableError(status)){
            var dialog = GooglePlayServicesUtil.getErrorDialog(status, this, status);
            dialog.show();
        }
        return false
    }

    //TODO : preguntar por el GPS si está  activo
}
