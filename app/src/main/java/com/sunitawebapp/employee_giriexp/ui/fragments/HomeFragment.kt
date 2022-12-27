package com.sunitawebapp.employee_giriexp.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.akexorcist.googledirection.DirectionCallback
import com.akexorcist.googledirection.model.Direction
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.sunitawebapp.admin_giriexp.sharepreference.SessionManager
import com.sunitawebapp.employee_giriexp.R
import com.sunitawebapp.employee_giriexp.callbacks.OnDialogButtonClickListener
import com.sunitawebapp.employee_giriexp.databinding.FragmentHomeBinding
import com.sunitawebapp.employee_giriexp.ext.showSnack
import com.sunitawebapp.employee_giriexp.features.dashboard.models.PostAttendanceReq
import com.sunitawebapp.employee_giriexp.features.dashboard.viewmodels.GetUserInfoViewModel
import com.sunitawebapp.employee_giriexp.features.dashboard.viewmodels.PostAttendanceViewModel
import com.sunitawebapp.employee_giriexp.retrofit.Resource
import com.sunitawebapp.employee_giriexp.utils.Constents.CheckInTime
import com.sunitawebapp.employee_giriexp.utils.Constents.CheckOutTime
import com.sunitawebapp.employee_giriexp.utils.MyDialog
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment(), View.OnClickListener, OnMapReadyCallback,
    OnDialogButtonClickListener, DirectionCallback {
    lateinit var binding: FragmentHomeBinding

    private var mFusedLocationClient: FusedLocationProviderClient? = null
    lateinit var mMap: GoogleMap

    var lat: Double = 0.0
    var lng: Double = 0.0
    var returnedAddress = ""

    private val postAttendanceViewModel: PostAttendanceViewModel by viewModels()
    private val getUserInfoViewModel: GetUserInfoViewModel by viewModels()

    private var inStatus = 0
    private var outStatus = 0

    private var radiusInMeters = 0
    private var userlat = 0.0
    private var userlng = 0.0
    var isofcarea =false
    private var polyLinePoints: ArrayList<LatLng>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        binding.apply {
            btnCheckIn.setOnClickListener(this@HomeFragment)
            btnCheckOut.setOnClickListener(this@HomeFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val smf = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        smf.getMapAsync(this)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());



        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1
                );
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1
                );
            }
        }

        /** Observe user info **/
        getUserInfoViewModel.userInfo(SessionManager(requireContext()).getuserloginid())
        mFusedLocationClient!!.lastLocation.addOnSuccessListener(requireActivity()) { location ->
            if (location != null) {
                lat = location.latitude
                lng = location.longitude

                getCurrentloc()
                //   https://stackoverflow.com/questions/16082622/check-if-marker-is-inside-circle-radius




            }
        }
        getUserInfoViewModel.userInfoLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    MyDialog.stopLoading()
                    it.data?.let { res ->
                        binding.tvcheckIn.text = res.inTime

                        binding.tvcheckout.text = res.outTime
                        CheckInTime= res.inTime
                        CheckOutTime= res.outTime
                        inStatus = res.in_status
                        outStatus = res.out_status
                        radiusInMeters= res.radiusInMeters
                        userlat= res.lat.toDouble()
                        userlng= res.lng.toDouble()
                        if  (inStatus==1){
                            mFusedLocationClient!!.lastLocation.addOnSuccessListener(requireActivity()) { location ->
                                if (location != null) {
                                    lat = location.latitude
                                    lng = location.longitude

                                    getCurrentloc()
                                    //   https://stackoverflow.com/questions/16082622/check-if-marker-is-inside-circle-radius




                                }
                            }

                        }

                    }
                }
                is Resource.Error -> {
                    MyDialog.stopLoading()
                    it.message?.let { message ->
                        binding.root.showSnack("An error occurred: $message")
                    }
                }
                is Resource.Loading -> {
                    activity?.let { fragmentActivity ->
                        MyDialog.showLoading(fragmentActivity)
                    }
                }
            }
        }

        /** Observe CheckIn CheckOut **/
        postAttendanceViewModel.postAttLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    MyDialog.stopLoading()
                    it.data?.let { res ->
                        if (res.DB_STATUS) {
                            binding.tvcheckIn.text = res.inTime
                            binding.tvcheckout.text = res.outTime

                            CheckInTime= res.inTime
                            CheckOutTime= res.outTime
                        }
                        binding.root.showSnack(res.DB_MSG)
                    }
                }
                is Resource.Error -> {
                    MyDialog.stopLoading()
                    it.message?.let { message ->
                        binding.root.showSnack("An error occurred: $message")
                    }
                }
                is Resource.Loading -> {
                    activity?.let { fragmentActivity ->
                        MyDialog.showLoading(fragmentActivity)
                    }
                }
            }
        }
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.btnCheckIn -> {
                isofcarea=false
                if (checkSelfPermission()) {
                    if (inStatus == 0) {
                        mFusedLocationClient!!.lastLocation.addOnSuccessListener(requireActivity()) { location ->
                            if (location != null) {
                                lat = location.latitude
                                lng = location.longitude

                                getCurrentloaction()
                                //   https://stackoverflow.com/questions/16082622/check-if-marker-is-inside-circle-radius

                                if (isofcarea) {
                                    postAttendance("in", lat.toString(), lng.toString())
                                }


                            }
                        }
                    }else{
                        binding.btnCheckOut.showSnack("You have already checked in")
                    }


                } else {
                    // No permission
                }
            }
            binding.btnCheckOut -> {
                if (checkSelfPermission()) {
                    if (outStatus == 0) {
                        MyDialog.showConfirmation(
                            "Alert !",
                            "Do you want to Check Out?",
                            requireActivity(),
                            "checkout",
                            this@HomeFragment
                        )
                    } else {
                        binding.btnCheckOut.showSnack("You have already checked out")
                    }

                } else {
                    // No permission
                }

            }
        }
    }

    private fun checkSelfPermission(): Boolean {
        return !(ActivityCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED)
    }

    private fun postAttendance(action: String, lat: String, lng: String) {
        if (action == "in") {
            postAttendanceViewModel.postAtt(
                PostAttendanceReq(
                    inorout = action,
                    inLat = lat,
                    inLocation = "",
                    inLong = lng,
                    outLat = "",
                    outLocation = "",
                    outLong = "",
                    tblsysuserlogin_id = SessionManager(requireContext()).getuserloginid()
                )
            )
        } else {
            postAttendanceViewModel.postAtt(
                PostAttendanceReq(
                    inorout = action,
                    inLat = "",
                    inLocation = "",
                    inLong = "",
                    outLat = lat,
                    outLocation = "",
                    outLong = lng,
                    tblsysuserlogin_id = SessionManager(requireContext()).getuserloginid()
                )
            )
        }

    }

    override fun onMapReady(map: GoogleMap) {
        mMap = map
    }

    private fun getCurrentloaction() : Boolean{
        // var  title=stringToLatLong("$lat,$lng")
        var getcurrarea =    mMap.addMarker(
            MarkerOptions()
                .position(LatLng(lat, lng))
                .title(returnedAddress)

        )




        var isofcareaCircle =      mMap.addCircle(
            CircleOptions()
                .center(LatLng(userlat, userlng))
                .radius(radiusInMeters.toDouble())
                .strokeWidth(5f)
                .strokeColor(Color.RED)
                .fillColor(0x550000FF)
        )
        val distance = FloatArray(2)

        Location.distanceBetween(
            getcurrarea.getPosition().latitude, getcurrarea.getPosition().longitude,
            isofcareaCircle.getCenter().latitude, isofcareaCircle.getCenter().longitude, distance
        )

        if (distance[0] > isofcareaCircle.getRadius()) {
            isofcarea=false
            binding.btnCheckIn.showSnack("you are not in office")
        } else {
            isofcarea=true
            Toast.makeText(requireContext(), "Inside", Toast.LENGTH_LONG).show()
        }

        val cameraPosition = CameraPosition.Builder().target(LatLng(lat, lng))
            .zoom(17f)
            .bearing(0f)
            .tilt(45f)
            .build()
        //    CameraUpdateFactory.newLatLngBounds(la)
        //    mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(getLatLngBoundsFromCircle(mycircle),30));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lng), 15f))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(lat, lng)))
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15f), 2000, null);

        return isofcarea

    }

    private fun getCurrentloc() {
        // var  title=stringToLatLong("$lat,$lng")
        mMap.addMarker(
            MarkerOptions()
                .position(LatLng(lat, lng))
                .title(returnedAddress)

        )
        //FIXME

        mMap.addCircle(
            CircleOptions()
                .center(LatLng(userlat, userlng))
                .radius(radiusInMeters.toDouble())
                .strokeWidth(5f)
                .strokeColor(Color.RED)
                .fillColor(0x550000FF)
        )


        val cameraPosition = CameraPosition.Builder().target(LatLng(lat, lng))
            .zoom(17f)
            .bearing(0f)
            .tilt(45f)
            .build()
        //    CameraUpdateFactory.newLatLngBounds(la)
        //    mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(getLatLngBoundsFromCircle(mycircle),30));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lng), 15f))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(lat, lng)))
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15f), 2000, null);



    }

    @SuppressLint("MissingPermission")
    override fun onPositiveButtonClicked(whatFor: String) {
        when (whatFor) {
            "checkout" -> {
                isofcarea=false
                mFusedLocationClient!!.lastLocation.addOnSuccessListener(requireActivity()) { location ->
                    if (location != null) {
                        lat = location.latitude
                        lng = location.longitude
                        getCurrentloaction()
                        if (isofcarea) {
                            postAttendance("out", lat.toString(), lng.toString())
                        }

                    }
                }
            }
        }
    }

    override fun onNegativeButtonClicked(whatFor: String) {
        TODO("Not yet implemented")
    }


    override fun onDirectionSuccess(direction: Direction?) {
        if (direction!!.isOK) {
            val route = direction.routeList[0]
            if (!route.legList.isEmpty()) {
                polyLinePoints = route.legList[0].directionPoint
            }
        }
    }

    override fun onDirectionFailure(t: Throwable) {

    }



}
