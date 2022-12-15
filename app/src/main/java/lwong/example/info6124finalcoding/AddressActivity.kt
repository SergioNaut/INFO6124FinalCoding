package lwong.example.info6124finalcoding

import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.google.android.gms.maps.model.LatLng
import lwong.example.info6124finalcoding.databinding.ActivityAddressBinding
import java.io.IOException
import java.time.LocalTime
import java.util.*

class AddressActivity : AppCompatActivity() {

    //LOCATION ACTIVITY
    private lateinit var binding: ActivityAddressBinding

    //Variables
    lateinit var editTextLat: EditText
    lateinit var editTextLon: EditText
    lateinit var textViewAddressFound: EditText
    lateinit var buttonUseNew: Button

    val defaultLat = 43.01244
    val defaultLon = -81.20018

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)

        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Set default values
        editTextLat = findViewById(R.id.editTextLat)
        editTextLat.setText(defaultLat.toString())

        editTextLon = findViewById(R.id.editTextLng)
        editTextLon.setText(defaultLon.toString())

        textViewAddressFound = findViewById(R.id.textViewAddressFound)
        textViewAddressFound.setText(getAddress(LatLng(defaultLat,defaultLon)))

        buttonUseNew = findViewById(R.id.buttonUseNew)
    }

    private fun getAddress(loc:LatLng): String? {
        val geocoder = Geocoder(this, Locale.getDefault())
        var addresses: List<Address>? = null
        try {
            addresses = geocoder.getFromLocation(loc!!.latitude, loc!!.longitude, 1)
        } catch (e1: IOException) {
            Log.e("Geocoding", getString(R.string.problem), e1)
        } catch (e2: IllegalArgumentException) {
            Log.e("Geocoding", getString(R.string.invalid)+
                    "Latitude = " + loc!!.latitude +
                    ", Longitude = " +
                    loc!!.longitude, e2)
        }
        // If the reverse geocode returned an address
        Log.i("Snippet Test Addresses","${addresses} ")
        if (addresses != null) {
            // Get the first address
            val address = addresses[0]
            val addressText = String.format(
                "%s",
                address.getAddressLine(0))
            Log.i("Snippet Test","Snippet Test")
            return addressText
        }
        else
        {
            Log.e("Geocoding", getString(R.string.noaddress))
            return ""
        }
    }

    fun onButtonSearch(view: View){
        var currentLat = editTextLat.text.toString()
        var currentLon = editTextLon.text.toString()

        textViewAddressFound.setText(getAddress(LatLng(currentLat.toDouble(),currentLon.toDouble())))
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
            //Go back to map screen

            var currentLat = editTextLat.text.toString()
            var currentLon = editTextLon.text.toString()
            var currentAddress = textViewAddressFound.text.toString()


            val intent = Intent(this, MapsActivity::class.java).apply {
                putExtra("lat",currentLat)
                putExtra("lon",currentLon)
                putExtra("address",currentAddress)
            }
            startActivity(intent)
            true
        }
        R.id.add_address ->{
            //does nothing
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
            //go to about screen
            val intent = Intent(this,AboutActivity::class.java)
            startActivity(intent)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}