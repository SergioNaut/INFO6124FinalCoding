package lwong.example.info6124finalcoding

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import lwong.example.info6124finalcoding.databinding.ActivityAboutBinding
import lwong.example.info6124finalcoding.databinding.ActivityMapsBinding
import java.util.*

class AboutActivity: AppCompatActivity() {

    public lateinit var binding: ActivityAboutBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
            true
        }
        R.id.add_address ->{
            val intent = Intent(this, AddressActivity::class.java)
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
            //Does nothing here

            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}