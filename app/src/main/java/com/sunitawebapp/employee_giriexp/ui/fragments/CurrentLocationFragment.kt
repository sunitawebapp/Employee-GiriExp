package com.sunitawebapp.employee_giriexp.ui.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.FirebaseDatabase
import com.google.maps.android.SphericalUtil
import com.sunitawebapp.employee_giriexp.R
import com.sunitawebapp.employee_giriexp.databinding.FragmentCurrentLocationBinding
import com.sunitawebapp.employee_giriexp.utils.Constents
import com.sunitawebapp.employee_giriexp.viewmodels.CurrentLocationViewModel
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CurrentLocationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CurrentLocationFragment : Fragment(), OnMapReadyCallback {
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    val currentLocationViewModel : CurrentLocationViewModel by viewModels()
//lateinit var currentLocationViewModel : CurrentLocationViewModel
    lateinit var mMap: GoogleMap
    private  var marker: Marker?=null
    lateinit var binding : FragmentCurrentLocationBinding
    var  lat : Double =0.0
    var lng : Double =0.0
    var  returnedAddress=""
    var distance: Double? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        binding=FragmentCurrentLocationBinding.inflate(inflater,container,false)
        binding.apply {
            //  btnLoc.setOnClickListener(this@CurrentLocationFragment)
            return binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        var smf =  getChildFragmentManager().findFragmentById(R.id.map) as SupportMapFragment
        smf.getMapAsync(this);
        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1);
            }else{
                ActivityCompat.requestPermissions(requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1);
            }
        }

        if (Constents.CheckInTime.equals("")){
            binding.conslocationDeactive.visibility=View.VISIBLE
            smf.getView()?.setVisibility(View.INVISIBLE);

            binding.btnExit.setOnClickListener {
                findNavController().navigate(R.id.homeFragment)
            }
        }else{
            smf.getView()?.setVisibility(View.VISIBLE);
            binding.conslocationDeactive.visibility=View.GONE

           /* mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

                mFusedLocationClient!!.getLastLocation().addOnSuccessListener(requireActivity()) { location ->
                    if (location != null) {

                        lat = location.getLatitude()
                        lng = location.getLongitude()
                        //    mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lng), 15f))
                        Toast.makeText(requireContext(),"$lat , $lng" , Toast.LENGTH_SHORT).show()
                        getCurrentloaction()
                        Log.d("sunita", "onViewCreated: "+"$lat , $lng")

                        var currentlat="$lat"
                        var currentlng="$lng"



                     *//*   var firebasedatabase= FirebaseDatabase.getInstance("https://locationonmap-483ef-default-rtdb.firebaseio.com/")
                        var databasereference= firebasedatabase.getReference("Driver")
                        var sourcelat=databasereference.child("SourceLocation").child("latitude").setValue("$lat")
                        var sourcelng=databasereference.child("SourceLocation").child("longitude").setValue("$lng")*//*
                    }
                }
*/
            var previouslocpoint :LatLng?=null
            var currentlocpoint :LatLng?=null
                currentLocationViewModel.getLocationData.observe(viewLifecycleOwner){
                lat = it.latitude.toString().toDouble()
                lng = it.longitude.toString().toDouble()

                 currentlocpoint = LatLng(lat, lng)

                binding.lngpoint="Longitude: \n${it.longitude.toString()}"
                binding.latpoint= "Latitude: \n${it.latitude.toString()}"

                getCurrentloaction()

                    previouslocpoint=currentlocpoint

                    if (previouslocpoint == null) {

                        distance = SphericalUtil.computeDistanceBetween(currentlocpoint, currentlocpoint)
                        Log.d("sunitachowdhury", "onViewCreated: "+currentlocpoint+" "+currentlocpoint)
                    }else{

                        distance = SphericalUtil.computeDistanceBetween(currentlocpoint, previouslocpoint);
                        binding.distanceCount = "Total Distance: \n"+String.format("%.2f", distance!! / 1000) + "km"
                        Log.d("sunitachowdhury", "onViewCreated: "+currentlocpoint+" "+previouslocpoint)
                    }



               /*     var firebasedatabase= FirebaseDatabase.getInstance("https://locationonmap-483ef-default-rtdb.firebaseio.com/")
                    var databasereference= firebasedatabase.getReference("GiriExp").child("Location").child("3")
                   databasereference.child("CurLatLng").child("Lat").setValue("$lat")
                   databasereference.child("CurLatLng").child("Lng").setValue("$lng")
                   databasereference.child("Distance").setValue(String.format("%.2f", distance!! / 1000) + "km")*/
                    Log.d("sunitachowdhury", "onViewCreated: "+String.format("%.2f", distance!! / 1000) + "km")
            }




        }


        super.onViewCreated(view, savedInstanceState)
    }

    override fun onMapReady(map: GoogleMap) {
        mMap = map;

    }
    fun getCurrentloaction(){
        var  title=stringToLatLong("$lat,$lng")
        if(marker == null){
            marker = mMap!!.addMarker(
                MarkerOptions()
                    .position(LatLng(lat, lng))
                    .title(returnedAddress)

            )
        } else {
            marker?.position = LatLng(lat, lng)
            marker?.title = returnedAddress
        }

        val cameraPosition = CameraPosition.Builder().target(LatLng(lat, lng))
            .zoom(17f)
            .bearing(0f)
            .tilt(45f)
            .build()
        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(LatLng(lat, lng)))
        mMap!!.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap!!.animateCamera(CameraUpdateFactory.zoomTo(12f), 2000, null);

    }

    private fun stringToLatLong(latLongStr: String): LatLng {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())


        val latlong = latLongStr.split(",").toTypedArray()
        val latitude = latlong[0].toDouble()
        val longitude = latlong[1].toDouble()

        val addresses: List<Address> = geocoder.getFromLocation(latitude, longitude, 1)
        val strReturnedAddress = StringBuilder("")
        var  returnedAdd = addresses[0]

        for (i in 0..returnedAdd.getMaxAddressLineIndex()) {
            strReturnedAddress.append(returnedAdd.getAddressLine(i)).append("\n")
        }
        returnedAddress = strReturnedAddress.toString()
        return LatLng(latitude, longitude)
    }
}
