package uz.muhammadziyo.homework33

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import uz.muhammadziyo.homework33.databinding.FragmentFragment2Binding
import uz.muhammadziyo.homework33.models.MyData
import java.util.concurrent.TimeUnit

class fragment2 : Fragment() {

    private lateinit var binding: FragmentFragment2Binding
    private lateinit var handler: Handler
    var time = 60
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFragment2Binding.inflate(layoutInflater)
        handler = Handler(requireActivity().mainLooper)
        handler.postDelayed(runnable2, 1000)
        val phone = MyData.phoneNumber
        val a = phone.toString().substring(0, 6)
        val b = phone.toString().substring(6, 9)
        binding.tvPhoneNumber.text = "Bir martalik kod ($a) $b-**-**"

        binding.edtPhone.addTextChangedListener {
            if (it.toString().length == 6) {
                verifiyCode()
            }
        }

        binding.btnRestart.setOnClickListener {
            sendVerificationCode(MyData.phoneNumber.toString())
        }


        return binding.root
    }

    private val runnable2 = object : Runnable {
        override fun run() {
            if (time.toString().length == 2) {
                binding.daqiqa.text = "00:$time"
            } else {
                binding.daqiqa.text = "00:0$time"
            }

            if (time != 0) {
                time--
                handler.postDelayed(this, 1000)
            } else {
                binding.daqiqa.text = "00:00"
                Toast.makeText(binding.root.context, "qayta urinip ko'ring", Toast.LENGTH_SHORT)
                    .show()
            }


        }
    }

    private fun verifiyCode() {
        val credential =
            PhoneAuthProvider.getCredential(
                MyData.storedVerificationId,
                binding.edtPhone.text.toString()
            )
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        MyData.auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) {
                if (it.isSuccessful) {
                    Toast.makeText(context, "Muvaffaqiyatli", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.fragment3)

                } else {

                    Toast.makeText(context, "Muvaffaqiyatsiz!!!", Toast.LENGTH_SHORT).show()
                    if (it.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(context, "Kod xato kiritildi", Toast.LENGTH_SHORT).show()
                    }
                }
            }
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
            findNavController().popBackStack()
            findNavController().navigate(R.id.fragment2)
        }

    }

}