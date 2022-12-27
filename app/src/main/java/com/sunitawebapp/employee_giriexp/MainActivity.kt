package com.sunitawebapp.employee_giriexp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import com.sunitawebapp.admin_giriexp.sharepreference.SessionManager
import com.sunitawebapp.employee_giriexp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() , NavController.OnDestinationChangedListener{
    lateinit  var binding : ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment


    var doubleBackToExitPressedOnce = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding= DataBindingUtil.setContentView(this,R.layout.activity_main)

        navHostFragment=   supportFragmentManager.findFragmentById(R.id.main_nav_host) as NavHostFragment
        navHostFragment.navController.addOnDestinationChangedListener(this)

        binding.toolbar.cons.visibility= View.GONE



        binding.navDrawer.tvMyLoc.setOnClickListener {
            binding.toolbar.cons.visibility= View.VISIBLE
            binding.toolbar.title.text="My Location"
            navHostFragment.navController.navigate(R.id.currentLocationFragment)
            binding.mainDrawer.closeDrawer(GravityCompat.START)}

        binding.navDrawer.tvMyAttendance.setOnClickListener {
            binding.toolbar.cons.visibility= View.VISIBLE
            binding.toolbar.title.text="My Attendance"
            navHostFragment.navController.navigate(R.id.attendanceFragment)
            binding.mainDrawer.closeDrawer(GravityCompat.START)}


        binding.navDrawer.tvHome.setOnClickListener {
            binding.toolbar.cons.visibility= View.VISIBLE
            binding.toolbar.title.text="Home"
            navHostFragment.navController.navigate(R.id.homeFragment)
            binding.mainDrawer.closeDrawer(GravityCompat.START)
        }

        binding.navDrawer.tvMyLeave.setOnClickListener {
            binding.toolbar.cons.visibility= View.VISIBLE
            binding.toolbar.title.text="Leave List"
            navHostFragment.navController.navigate(R.id.myLeavesListFragment)
            binding.mainDrawer.closeDrawer(GravityCompat.START)
        }
        binding.navDrawer.tvApplyLeave.setOnClickListener {
            binding.toolbar.cons.visibility= View.VISIBLE
            binding.toolbar.title.text="Leave Application"
            navHostFragment.navController.navigate(R.id.applyLeaveFragment)
            binding.mainDrawer.closeDrawer(GravityCompat.START)
        }

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

                binding.toolbar.menubtn.setOnClickListener {
                    binding.mainDrawer.openDrawer(Gravity.LEFT)
                    var sessionManager= SessionManager(this)
                    binding.navDrawer.tvName.setText(sessionManager.getusername())
                    binding.navDrawer.tvEmail.setText(sessionManager.getemail())
                }
            }
            R.id.loginFragment->{
                binding.toolbar.cons.visibility= View.GONE
            }
            R.id.splashFragment->{
                binding.toolbar.cons.visibility= View.GONE
            }
        }
    }

    override fun onBackPressed() {
        var sessionManager= SessionManager(this)
        if(sessionManager.isLogged()){
            if ( navHostFragment?.childFragmentManager?.backStackEntryCount != 2) {
                super.onBackPressed()
            } else {
                if (doubleBackToExitPressedOnce) {
                    finishAffinity()
                    super.onBackPressed()
                } else {
                    doubleBackToExitPressedOnce = true
                    Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
                    Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 1500)
                }
            }
        }else{
            if ( navHostFragment?.childFragmentManager?.backStackEntryCount != 5) {
                super.onBackPressed()
            } else {
                if (doubleBackToExitPressedOnce) {
                    finishAffinity()
                    super.onBackPressed()
                } else {
                    doubleBackToExitPressedOnce = true
                    Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
                    Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 1500)
                }
            }
        }

    }
}
