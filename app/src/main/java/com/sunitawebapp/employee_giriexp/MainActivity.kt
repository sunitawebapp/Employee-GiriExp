package com.sunitawebapp.employee_giriexp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import com.sunitawebapp.employee_giriexp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() , NavController.OnDestinationChangedListener{
    lateinit  var binding : ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding= DataBindingUtil.setContentView(this,R.layout.activity_main)

        navHostFragment=   supportFragmentManager.findFragmentById(R.id.main_nav_host) as NavHostFragment
        navHostFragment.navController.addOnDestinationChangedListener(this)

        binding.toolbar.cons.visibility= View.GONE
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {
            R.id.homeFragment->{
                binding.toolbar.cons.visibility= View.VISIBLE
                binding.toolbar.title.text="Home"
            }
            R.id.loginFragment->{
                binding.toolbar.cons.visibility= View.GONE
            }
            R.id.splashFragment->{
                binding.toolbar.cons.visibility= View.GONE
            }
        }
    }
}
