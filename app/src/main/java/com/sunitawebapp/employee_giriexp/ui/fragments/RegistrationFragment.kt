package com.sunitawebapp.employee_giriexp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sunitawebapp.employee_giriexp.R
import com.sunitawebapp.employee_giriexp.databinding.FragmentRegistrationBinding
import com.sunitawebapp.employee_giriexp.retrofit.model.Request.RegistrationReq
import com.sunitawebapp.employee_giriexp.retrofit.model.response.StationListResItem
import com.sunitawebapp.employee_giriexp.retrofit.Resource
import com.sunitawebapp.employee_giriexp.utils.MethodClass
import com.sunitawebapp.employee_giriexp.utils.MyDialog
import com.sunitawebapp.employee_giriexp.viewmodels.RegistrationViewModel
import com.sunitawebapp.employee_giriexp.viewmodels.StationListViewModel


class RegistrationFragment : Fragment() ,View.OnClickListener, AdapterView.OnItemSelectedListener {
lateinit var binding: FragmentRegistrationBinding
val registrationViewModel : RegistrationViewModel by viewModels()
    val stationListViewModel : StationListViewModel by viewModels()
    var cityCode =""
    var stationList : MutableList<StationListResItem> = ArrayList()
     var stationListResItem : ArrayList<String> = ArrayList()
    var tblmastcodecity_id =0
    var manager_tblsysuserlogin_id =0
    var dob : String =""
    val registrationFragmentArgs : RegistrationFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentRegistrationBinding.inflate(inflater,container, false)
        // Inflate the layout for this fragment

        binding.apply {
            btnRegister.setOnClickListener(this@RegistrationFragment)
            spinStation.onItemSelectedListener = this@RegistrationFragment
            tvDOB.setOnClickListener(this@RegistrationFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

     binding.etnMobNo.setText(registrationFragmentArgs.mobile)
        stationListViewModel.StationList()
        stationListViewModel.StationListLivedata.observe(viewLifecycleOwner){
            stationListResItem.add("Select Station")
            stationList.add(StationListResItem("","","",0,0,0))
            for (i in it.data!!){
                stationList.add(i)
                stationListResItem.add(i.cityCode)
            }
            // Create the instance of ArrayAdapter
            // having the list of stationList
            val ad = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, stationListResItem)

            // set simple layout resource file
            // for each item of spinner
            ad.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
            )

            // Set the ArrayAdapter (ad) data on the
            // Spinner which binds data to spinner
            binding.spinStation.setAdapter(ad)
        }

        registrationViewModel.registerLivedata.observe(viewLifecycleOwner){res->
            when(res) {
                is Resource.Success -> {
                    res.data?.let {
                        MyDialog.stopLoading()

                            Toast.makeText(requireContext(), res.data.MSG, Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.thankUregisterFragment)


                    }
                }
                is Resource.Error -> {

                }
                is Resource.Loading -> {
                    MyDialog.showLoading(requireContext())
                }
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onClick(view: View?) {
        when(view){
            binding.btnRegister->{
                if(binding.etnName.text.length==0){
                    binding.etnName.setError("Enter your Name")
                }else if(binding.etnEmail.text.length==0){
                    binding.etnEmail.setError("Enter your email")
                }else if(binding.etnMobNo.text.length==0){
                    binding.etnMobNo.setError("Enter your Mobile Number")
                }else if(binding.etnPassword.text.length==0){
                    binding.etnPassword.setError("Enter your Password")
                }else if(binding.tvDOB.text.toString().equals("")){
                    Toast.makeText(requireContext(),"Select DOB", Toast.LENGTH_SHORT).show()
                }else if(cityCode.equals("0")){
                    Toast.makeText(requireContext(),"Select City", Toast.LENGTH_SHORT).show()
                }else {

                    registrationViewModel.Registration(
                        RegistrationReq(
                            email = binding.etnEmail.text.toString(),
                            fullName = binding.etnName.text.toString(),
                            manager_tblsysuserlogin_id = manager_tblsysuserlogin_id,
                            mobile = binding.etnMobNo.text.toString(),
                            password = binding.etnPassword.text.toString(),
                            dob=dob,
                            tblmastcodecity_id = cityCode.toInt(),
                            tblmastusertype_id = 3
                        )
                    )
                }
            }

            binding.tvDOB->{
            dob=   MethodClass.openCalenderDialog(requireContext(),  binding.tvDOB)
            }

            }
        }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
        cityCode=stationList[pos].tblmastcodecity_id.toString()
         manager_tblsysuserlogin_id=stationList.get(pos).manager_tblsysuserlogin_id
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }
}



