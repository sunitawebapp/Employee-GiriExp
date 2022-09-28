package com.sunitawebapp.employee_giriexp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.sunitawebapp.employee_giriexp.R
import com.sunitawebapp.employee_giriexp.databinding.FragmentPhNoVerifyBinding

import com.sunitawebapp.employee_giriexp.databinding.FragmentRegistrationBinding


class PhNoVerifyFragment : Fragment()  ,View.OnClickListener{
    lateinit var binding: FragmentPhNoVerifyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentPhNoVerifyBinding.inflate(inflater,container, false)
        // Inflate the layout for this fragment
        binding.apply {
            btnSend.setOnClickListener(this@PhNoVerifyFragment)
        }
        return binding.root
    }
    override fun onClick(view: View?) {
        when(view){
            binding.btnSend->{
                findNavController().navigate(R.id.registrationFragment)
            }
        }
    }

}
