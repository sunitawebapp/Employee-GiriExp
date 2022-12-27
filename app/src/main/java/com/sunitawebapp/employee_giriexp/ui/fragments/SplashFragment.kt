package com.sunitawebapp.employee_giriexp.ui.fragments


import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sunitawebapp.employee_giriexp.BuildConfig
import com.sunitawebapp.employee_giriexp.R
import com.sunitawebapp.employee_giriexp.databinding.FragmentSplashBinding
import com.sunitawebapp.employee_giriexp.receiver.NetworkChangeReceiver
import com.sunitawebapp.employee_giriexp.retrofit.Resource
import com.sunitawebapp.employee_giriexp.utils.MyDialog
import com.sunitawebapp.employee_giriexp.viewmodels.AppVersionAvailableViewmodel


class SplashFragment : Fragment() {

    private val appVersionAvailableViewmodel: AppVersionAvailableViewmodel by viewModels()
    lateinit var binding: FragmentSplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.splashVersion.setText("Version " + com.sunitawebapp.employee_giriexp.BuildConfig.VERSION_NAME)


        var status = NetworkChangeReceiver.getConnectivityStatusString(requireContext())
        if (status!!.isEmpty() || status.equals("No internet is available") || status.equals("No Internet Connection")) {
        } else {
            appVersionAvailableViewmodel.isUpdateAvailable()
        }


        appVersionAvailableViewmodel.appVersionAvailLivedata.observe(viewLifecycleOwner) { res ->
            when (res) {
                is Resource.Success -> {
                    res.data?.let {
                        MyDialog.stopLoading()
                        if (BuildConfig.VERSION_NAME != res.data.dbVersion) {
                            findNavController().navigate(R.id.versionAvailableFragment)
                        } else {
                            val secondsDelayed = 1
                            Handler().postDelayed({

                                findNavController().navigate(R.id.loginFragment)

                            }, (secondsDelayed * 2000).toLong())
                        }
                    }
                }
                is Resource.Error -> {

                }
                is Resource.Loading -> {
                    MyDialog.showLoading(requireContext())
                }
            }
        }
    }
}
