package lwong.example.info6124finalcoding

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import lwong.example.info6124finalcoding.databinding.ActivitySetcalendarBinding

class SetCalendarEventActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySetcalendarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        binding = ActivitySetcalendarBinding.inflate(layoutInflater)
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
            //Does nothing here

            true
        }
        R.id.about ->{
            //Does nothing here
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}