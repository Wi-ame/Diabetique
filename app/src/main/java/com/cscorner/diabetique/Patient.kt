package com.cscorner.diabetique

import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase


class Patient(
    fullName: String,
    email: String,
    password: String,
    passwordconf: String,
    phoneNumber: String,
    doctor: String,
    allergie: String,
    gender : String,
    age  :String,
    diabete :String,
    poids: String,
    taille: String,
    activite: String
) {
    var fullName: String = fullName
    var email: String = email
    var password: String = password
    var passwordconf: String = passwordconf
    var phoneNumber: String = phoneNumber
    var doctor: String = doctor
    var allergie: String = allergie
    var gender : String =gender
    val age :String =age
    val diabete: String = diabete
    val poids :String= poids
    val taille :String= taille
    val activite :String= activite
}




