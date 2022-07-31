package uz.muhammadziyo.homework33.models

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider

object MyData {
    var phoneNumber:String? = null
     lateinit var auth: FirebaseAuth
    lateinit var storedVerificationId: String
     lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
}