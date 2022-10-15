package com.example.notesapp

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.commit
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

//    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
//
//        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
//
//        drawerLayout.addDrawerListener(toggle)
//        toggle.syncState()

//        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportFragmentManager.commit {
            replace(R.id.notesFragment, NotesFragment())
            addToBackStack(null)
        }

//        val navView = findViewById<NavigationView>(R.id.navView)

//        navView.setNavigationItemSelectedListener {
//            when(it.itemId){
//                R.id.dashboard -> {
//                    supportFragmentManager.commit {
//                        replace(R.id.notesFragment, NotesFragment())
//                    }
//                }
//                R.id.deleted -> {
//                    Log.d("Main Activity", "Move to deleted")
//                }
//                R.id.settings -> {
//                    Log.d("Main Activity", "Move to Settings")
//                }
//            }
//            drawerLayout.closeDrawers()
//            true
//        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#576DE4")))

    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if(toggle.onOptionsItemSelected(item)){
//            return true
//        }
//        return super.onOptionsItemSelected(item)
//    }

}