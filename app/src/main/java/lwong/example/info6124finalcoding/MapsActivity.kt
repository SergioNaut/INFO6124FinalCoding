package lwong.example.info6124finalcoding

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import lwong.example.info6124finalcoding.databinding.ActivityMapsBinding
import java.io.IOException
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    //var fanshawe = LatLng(43.012440,-81.200180)

     var newLat = "43.012440"
    var newLon = "-81.200180"
    var markerTitle = "Fanshawe"
    var extraMarker = false
    var exMarkerLat = "0.0"
    var exMarkerLon = "0.0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val bundle = intent.extras

        if (bundle != null) {
            newLat = bundle.getString("lat","0.0")
        }
        if (bundle != null) {
            newLon = bundle.getString("lon","0.0")
        }
        if(bundle != null){
            markerTitle = bundle.getString("address","")
        }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val MyLocation = LatLng(newLat.toDouble(), newLon.toDouble())
        mMap.addMarker(MarkerOptions()
            .position(MyLocation)
            .title(markerTitle)
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(MyLocation))

        setMapLongClick(mMap)


    }

    //Create a Menu Button
    override fun onCreateOptionsMenu(menu: Menu?):Boolean{
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_options,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId){
        //Change page based on user's selection
        R.id.normal_map ->{
            //Does nothing
            true
        }
        R.id.add_address ->{
            val intent = Intent(this, AddressActivity::class.java).apply {
                putExtra("extraLat",exMarkerLat)
                putExtra("extraLon",exMarkerLon)
            }
            startActivity(intent)

            true
        }
        R.id.calendar ->{
            //Go to calendar page
            val intent = Intent(Intent.ACTION_INSERT).apply {
                data = CalendarContract.Events.CONTENT_URI
                putExtra(CalendarContract.Events.TITLE, "Meeting")
                putExtra(CalendarContract.Events.EVENT_LOCATION, "The Location")
                //Need to get current time
                putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, Calendar.HOUR)
                putExtra(CalendarContract.EXTRA_EVENT_END_TIME, Calendar.HOUR + 1)
            }

            startActivity(intent)

            true
        }
        R.id.about ->{
            //
            val intent = Intent(this,AboutActivity::class.java)
            startActivity(intent)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    // adding markers onLongClick on the map
    private fun setMapLongClick(map: GoogleMap) {

        if(!extraMarker){
            extraMarker = true
            map.setOnMapLongClickListener { latLng ->
                // A Snippet is Additional text that's displayed below the title.
                exMarkerLat = latLng.latitude.toString()
                exMarkerLon = latLng.longitude.toString()

                val snippet = String.format(
                    Locale.getDefault(),
                    "Lat: %1$.5f, Long: %2$.5f",
                    latLng.latitude,
                    latLng.longitude
                )
                map.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title("New Location: ")
                        .snippet(snippet)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
                )
            }
        }
    }
}