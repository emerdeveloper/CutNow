package co.com.emegonza.cutnow.activities.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import co.com.emegonza.cutnow.MainActivity
import co.com.emegonza.cutnow.R
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.GooglePlayServicesUtil
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng

class MapsFragment : Fragment(),
    OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener,
    LocationListener {

    private val MY_LOCATION_REQUEST_CODE = 1
    private lateinit var mMap: GoogleMap
    var fragment : SupportMapFragment? = null
    var contextActivity : Context? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        contextActivity = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

     override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         // Inflate the layout for this fragment
         val rootView : View = inflater.inflate(R.layout.activity_maps, container, false)

         if (isGooglePlayServicesAvailable())
         // Obtain the SupportMapFragment and get notified when the map is ready to be used.
         fragment = childFragmentManager
             .findFragmentById(R.id.map) as SupportMapFragment
         fragment?.getMapAsync(this)

         return rootView
    }


    //Events Maps
    fun enableMyLocationIfPermitted() {
        if (ContextCompat.checkSelfPermission(contextActivity!!, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                (MainActivity()),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
                MY_LOCATION_REQUEST_CODE
            )

            // Show rationale and request permission.
            Toast.makeText(
                contextActivity, "Location permission not granted, " + "showing default location",
                Toast.LENGTH_SHORT
            ).show()

        } else {

            mMap.isMyLocationEnabled = true
            mMap.setOnMyLocationButtonClickListener(this)
            mMap.setOnMyLocationClickListener(this)
            //contextActivity.
            var locationManager : LocationManager = getActivity()!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager//ContextCompat.getSystemService(contextActivity!!, LOCATION_SERVICE) as LocationManager
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
            contextActivity, "Location permission not granted, " + "showing default location",
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
        //Method Override
    }

    override fun onProviderDisabled(provider: String?) {
        //Method Override
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        //Method Override
    }



    //Google playServices
    private fun isGooglePlayServicesAvailable(): Boolean {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val status = googleApiAvailability.isGooglePlayServicesAvailable(contextActivity)
        if (ConnectionResult.SUCCESS == status)
            return true
        else  if (googleApiAvailability.isUserResolvableError(status)){
            var dialog = GooglePlayServicesUtil.getErrorDialog(status, getActivity(), status);
            dialog.show();
        }
        return false
    }

    //TODO : preguntar por el GPS si está  activo
}
