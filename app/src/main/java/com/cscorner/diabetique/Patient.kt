package com.cscorner.diabetique

import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.IgnoreExtraProperties
import java.util.UUID

@IgnoreExtraProperties
class Patient() {
    var statut: String=" "
    var id: String = UUID.randomUUID().toString()
    var fullName: String = ""
    var email: String = ""
    var password: String = ""
    var phoneNumber: String = ""
    var doctor: String = ""
    var allergie: String = ""
    var gender : String = ""
    var age :String = ""
    var diabete: String = ""
    var poids :String= ""
    var taille :String= ""
    var activite :String= ""


    constructor(
        fullName: String,
        email: String,
        password: String,
        phoneNumber: String,
        doctor: String,
        allergie: String,
        gender : String,
        age  :String,
        diabete :String,
        poids: String,
        taille: String,
        activite: String
    ) : this() {
        this.fullName = fullName
        this.email = email
        this.password = password
        this.phoneNumber = phoneNumber
        this.doctor = doctor
        this.allergie = allergie
        this.gender = gender
        this.age = age
        this.diabete = diabete
        this.poids = poids
        this.taille = taille
        this.activite = activite
    }
}


