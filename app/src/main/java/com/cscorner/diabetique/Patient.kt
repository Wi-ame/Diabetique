package com.cscorner.diabetique

import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase


class Patient: ViewModel() {
    var fullName: String = ""
    var email: String = ""
    var password: String = ""
    var passwordconf: String = ""
    var phoneNumber: String = ""
    var doctor: String = ""
    var physqiue: String = ""
    var weight: Double = 0.0
    var height: Double = 0.0
    var gender: String=""
    var Age   :  String=""
    var allergie :String=""
    var typed :String =""
    var glycemie: Double = 0.0
  fun savePage1Data(fullName: String, email: String, password: String, passwordConf: String, phoneNumber: String, doctor: String) {
    this.fullName = fullName
    this.email = email
    this.password = password
    this.passwordconf = passwordConf
    this.phoneNumber = phoneNumber
    this.doctor = doctor
  }

  fun savePage2Data(gender: String) {
    this.gender = gender

  }
  fun savePage3Data( age: String) {
    this.Age = age

  }

  fun savePage5Data(typed: String) {
    this.typed = typed

  }
  fun savePage6Data(weight: Double, height:Double ){
    this.weight = weight
    this.height=height

  }
  fun savePage7Data( physique:String ) {
    this.physqiue = physique
  }
  fun savePage8Data( allergie:String ){
    this.allergie= allergie
  }
    fun saveDataToFirebase() {
        val database = FirebaseDatabase.getInstance().reference.child("patients")
        val patientId = database.push().key // Génère une clé unique pour le patient
        val patientData = hashMapOf(
            "fullName" to fullName,
            "email" to email,
            "password" to password,
            "passwordconf" to passwordconf,
            "phoneNumber" to phoneNumber,
            "doctor" to doctor,
            "physique" to physqiue,
            "weight" to weight,
            "height" to height,
            "gender" to gender,
            "age" to Age,
            "allergie" to allergie,
            "typed" to typed,
            "glycemie" to glycemie
        )

        patientId?.let {
            database.child(it).setValue(patientData)
                .addOnSuccessListener {
                }
                .addOnFailureListener {
                }
        }
    }

}