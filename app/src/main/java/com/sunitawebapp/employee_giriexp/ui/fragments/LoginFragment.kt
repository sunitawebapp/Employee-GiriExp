package com.sunitawebapp.employee_giriexp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sunitawebapp.admin_giriexp.sharepreference.SessionManager
import com.sunitawebapp.employee_giriexp.R
import com.sunitawebapp.employee_giriexp.databinding.FragmentLoginBinding
import com.sunitawebapp.employee_giriexp.receiver.NetworkChangeReceiver
import com.sunitawebapp.employee_giriexp.retrofit.model.Request.LoginReq
import com.sunitawebapp.employee_giriexp.retrofit.Resource
import com.sunitawebapp.employee_giriexp.utils.MyDialog
import com.sunitawebapp.employee_giriexp.viewmodels.LoginViewModel

class LoginFragment : Fragment() ,View.OnClickListener{
    lateinit var binding : FragmentLoginBinding
    private val loginViewModel : LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentLoginBinding.inflate(inflater, container, false)
        binding.apply {
            btnLogin.setOnClickListener(this@LoginFragment)
            tvRegister.setOnClickListener(this@LoginFragment)
            return binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loginViewModel.loginLiveData.observe(viewLifecycleOwner){res->
            when(res) {
                is Resource.Success -> {
                    res.data?.let {
                        MyDialog.stopLoading()
                        if (res.data.status.toString()=="1"){
                            var sessionManager= SessionManager(requireContext())
                            sessionManager.setusertypeid(res.data.tblmastusertype_id)
                            sessionManager.setuserloginid(res.data.tblsysuserlogin_id)
                            sessionManager.setusername(res.data.username)
                            sessionManager.setemail(res.data.email)
                            sessionManager.setLogged(true)
                            Toast.makeText(requireContext(), "login suncess", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.homeFragment)

                        }else{
                            Toast.makeText(requireContext(), "login unsuncess", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.loginFragment)
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
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onClick(view: View?) {
        when(view){
            binding.btnLogin-> {
                var status = NetworkChangeReceiver.getConnectivityStatusString(requireContext())
                if (status!!.isEmpty() || status.equals("No internet is available") || status.equals(
                        "No Internet Connection"
                    )
                ) {
                } else {
                    if (binding.etnMobNo.text.length == 0) {
                        binding.etnMobNo.setError("Enter your Mobile Number")
                    } else if (binding.etnPassword.text.length == 0) {
                        binding.etnPassword.setError("Enter your Password")
                    } else {
                        loginViewModel.login(
                            LoginReq(
                                password = binding.etnPassword.text.toString(),
                                userName = binding.etnMobNo.text.toString(),
                                userTypeID = 3
                            )
                        )
                    }
                }
            }
            binding.tvRegister->{
               findNavController().navigate(R.id.phNoVerifyFragment)
                //findNavController().navigate(R.id.registrationFragment)
            }
        }
    }
}
