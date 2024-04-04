package com.cscorner.diabetique

    class Doctor {
        var fullName: String = ""
        var email: String = ""
        var password: String = ""

        var phoneNumber: String = ""
        var addresse: String = ""

        // Constructeur sans paramètres
        constructor() {

        }
        // Constructeur avec paramètres
        constructor(
            fullName: String,
            email: String,
            password: String,

            phoneNumber: String,
            addresse: String
        ) {
            this.fullName = fullName
            this.email = email
            this.password = password

            this.phoneNumber = phoneNumber
            this.addresse = addresse
        }
    }




