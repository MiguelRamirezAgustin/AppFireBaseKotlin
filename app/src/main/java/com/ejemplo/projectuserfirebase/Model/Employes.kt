package com.ejemplo.projectuserfirebase.Model

import com.google.firebase.database.IgnoreExtraProperties
import java.util.*

@IgnoreExtraProperties
data class Employes(
    var key:String? = "",
    var name: String? = "",
    var lastName: String? = "",
    var sexo: Boolean? = false,
    var phone: String? = "",
    var email: String? = "",
    var activo: Boolean? = false,
    var dateBirth: String? = "",
    var password: String? = ""
)