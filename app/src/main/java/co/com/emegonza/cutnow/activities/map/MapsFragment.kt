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
import co.com.emegonza.cutnow.contracts.FirebaseDelegate
import co.com.emegonza.cutnow.model.User
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.GooglePlayServicesUtil
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.*
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser

class MapsFragment : Fragment(),
    OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener,
    LocationListener,
    FirebaseDelegate {

    private val MY_LOCATION_REQUEST_CODE = 1
    private lateinit var mMap: GoogleMap
    private var fragment : SupportMapFragment? = null
    private var tmpRealTimeMarker: ArrayList<Marker> = ArrayList()
    private var realTimeMarker: ArrayList<Marker> = ArrayList()
    private var mainActivity: MainActivity = MainActivity()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
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
         mainActivity =  activity as MainActivity

         if (isGooglePlayServicesAvailable())
         // Obtain the SupportMapFragment and get notified when the map is ready to be used.
         fragment = childFragmentManager
             .findFragmentById(R.id.map) as SupportMapFragment
         fragment?.getMapAsync(this)

         return rootView
    }


    //Events Maps
    private fun enableMyLocationIfPermitted() {
        if (ContextCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                (activity!!),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
                MY_LOCATION_REQUEST_CODE
            )

            // Show rationale and request permission.
            Toast.makeText(
                activity, "Location permission not granted, " + "showing default location",
                Toast.LENGTH_SHORT
            ).show()

        } else {

            mMap.isMyLocationEnabled = true
            mMap.setOnMyLocationButtonClickListener(this)
            mMap.setOnMyLocationClickListener(this)
            //contextActivity.
            var locationManager : LocationManager = activity!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            var bestProvider : String = locationManager.getBestProvider(Criteria(), true)
            var location = locationManager.getLastKnownLocation(bestProvider);
            if (location != null) {
                onLocationChanged(location);
            }
            //locationManager.requestLocationUpdates(bestProvider, 20000, 0f, this)
        }
    }

    private fun showDefaultLocation() {
        Toast.makeText(
            activity, "Location permission not granted, " + "showing default location",
            Toast.LENGTH_SHORT
        ).show()
        val redmond = LatLng(47.6739881, -122.121512)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(redmond))
    }


    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        enableMyLocationIfPermitted()

        showMarker(mainActivity.data!!)

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
    private fun createCircle(location: Location) : CircleOptions {
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
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16f));
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
        val status = googleApiAvailability.isGooglePlayServicesAvailable(activity)
        if (ConnectionResult.SUCCESS == status)
            return true
        else  if (googleApiAvailability.isUserResolvableError(status)){
            var dialog = GooglePlayServicesUtil.getErrorDialog(status, activity, status);
            dialog.show();
        }
        return false
    }

    override fun onUpdateBarberData(barber: JSONObject)
    {
        showMarker(barber)
    }

    private fun showMarker(barber: JSONObject)
    {
        for (marker in realTimeMarker)
        {
            marker.remove()
        }

        for (item in barber)
        {
            var location = ((item.value as JSONObject).get("location") as JSONObject).get("latLang") as MarkerOptions
            tmpRealTimeMarker.add(mMap.addMarker(location))
            /*
            for (json in (item.value as JSONObject))
            {
                if (json.key!!.equals("location"))
                {
                    var location =  (json.value as JSONObject).get("latLang") as MarkerOptions
                    tmpRealTimeMarker.add(mMap.addMarker(location))
                }
            }*/
        }
        realTimeMarker.clear()
        realTimeMarker.addAll(tmpRealTimeMarker)
    }
    //TODO : preguntar por el GPS si está  activo
}
