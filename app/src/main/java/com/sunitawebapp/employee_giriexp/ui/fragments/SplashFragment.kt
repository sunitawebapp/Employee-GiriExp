package com.sunitawebapp.employee_giriexp.ui.fragments


import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sunitawebapp.employee_giriexp.R
import com.sunitawebapp.employee_giriexp.databinding.FragmentSplashBinding


class SplashFragment : Fragment() {
    lateinit var binding : FragmentSplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.splashVersion.setText("Version " + com.sunitawebapp.employee_giriexp.BuildConfig.VERSION_CODE)



        val secondsDelayed = 1
        Handler().postDelayed({

            findNavController().navigate(R.id.loginFragment)

        }, (secondsDelayed * 2000).toLong())
        super.onViewCreated(view, savedInstanceState)
    }

}
