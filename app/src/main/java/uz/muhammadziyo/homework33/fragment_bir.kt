package uz.muhammadziyo.homework33

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import uz.muhammadziyo.homework33.databinding.FragmentBirBinding
import uz.muhammadziyo.homework33.models.MyData
import java.util.concurrent.TimeUnit


class fragment_bir : Fragment() {

    private lateinit var binding: FragmentBirBinding




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBirBinding.inflate(layoutInflater)
        MyData.auth = FirebaseAuth.getInstance()
        MyData.auth.setLanguageCode("uz")

        binding.btnSign.setOnClickListener {
            val phoneNumber = binding.edtPhone.text.toString()
                MyData.phoneNumber = phoneNumber
                sendVerificationCode(phoneNumber)

        }

        return binding.root
    }

    private fun sendVerificationCode(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(MyData.auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(p0: PhoneAuthCredential) {
            Toast.makeText(binding.root.context, "completed", Toast.LENGTH_SHORT).show()
        }

        override fun onVerificationFailed(p0: FirebaseException) {
            Snackbar.make(binding.root, "Xatolik", Snackbar.LENGTH_LONG).show()

        }
        override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
            super.onCodeSent(p0, p1)
            MyData.storedVerificationId = p0
            MyData.resendToken = p1
            findNavController().navigate(R.id.fragment2)
        }

    }



}

