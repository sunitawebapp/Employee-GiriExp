package com.sunitawebapp.employee_giriexp.ui.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.sunitawebapp.employee_giriexp.R
import com.sunitawebapp.employee_giriexp.databinding.FragmentPhNoVerifyBinding
import java.util.concurrent.TimeUnit


class PhNoVerifyFragment : Fragment()  ,View.OnClickListener{
    lateinit var binding: FragmentPhNoVerifyBinding
    //lateinit  var code  : PhoneAuthCredential
    //It is the verification id that will be sent to the user
    private var mVerificationId: String? = null
    //firebase auth object
    var mAuth: FirebaseAuth? = null

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
            btnVerify.setOnClickListener(this@PhNoVerifyFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //initializing objects
        mAuth = FirebaseAuth.getInstance();


        //getting mobile number from the previous activity
        //and sending the verification code to the number
   /*     var intent =requireActivity().getIntent()
        val mobile = intent.getStringExtra("mobile")*/


        super.onViewCreated(view, savedInstanceState)
    }
    override fun onClick(view: View?) {
        when(view){
            binding.btnSend->{
                if (binding.etnMobNo.text.length == 0) {
                    binding.etnMobNo.setError("Enter your Mobile Number")
                }else {
                 //   findNavController().navigate(com.sunitawebapp.employee_giriexp.R.id.registrationFragment)
                   sendVerificationCode(binding.etnMobNo.text.toString())
                }

            }

            binding.  btnVerify-> {
              var action=  PhNoVerifyFragmentDirections.actionPhNoVerifyFragmentToRegistrationFragment(  binding.etnMobNo.text.toString())
                findNavController().navigate(action)
                //verifying the code
              //  verifyVerificationCode(  binding.  etnOTP.text.toString())
            }
        }
    }

    //the method is sending verification code
    //the country id is concatenated
    //you can take the country id as user input as well
    private fun sendVerificationCode(mobile: String) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            "+91$mobile",
            60,
            TimeUnit.SECONDS,
           requireActivity(),
            mCallbacks
        )
    }


    //the callback to detect the verification status
    private val mCallbacks: OnVerificationStateChangedCallbacks =
        object : OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {

                //Getting the code sent by SMS
                var code = phoneAuthCredential.smsCode

                //sometime the code is not detected automatically
                //in this case the code will be null
                //so user has to manually enter the code
                if (code != null) {

                 binding.  etnOTP.setText(code)

                   // binding.  btnSend.setOnClickListener {
                        //verifying the code
                        verifyVerificationCode(code)
                   // }


                }
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
            }

            override fun onCodeSent(s: String, forceResendingToken: ForceResendingToken) {
                super.onCodeSent(s, forceResendingToken)
                binding.  etnOTP.visibility=View.VISIBLE
                binding.  btnVerify.visibility=View.VISIBLE

                //storing the verification id that is sent to the user
                mVerificationId = s
            }
        }
    private fun verifyVerificationCode(code: String) {
        //creating the credential
        val credential = PhoneAuthProvider.getCredential(mVerificationId!!, code)

        //signing the user
        signInWithPhoneAuthCredential(credential)
    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity(),
                OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) {

                        //verification successful we will start the registrationFragment
                        findNavController().navigate(com.sunitawebapp.employee_giriexp.R.id.registrationFragment)
                    } else {

                        //verification unsuccessful.. display an error message
                        var message = "Somthing is wrong, we will fix it soon..."
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            message = "Invalid code entered..."
                        }
                        Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()

                    }
                })
    }

}
