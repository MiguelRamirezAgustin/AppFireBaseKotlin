package com.ejemplo.projectuserfirebase

import com.google.firebase.database.IgnoreExtraProperties
import java.util.*

@IgnoreExtraProperties
data class Employes(
    var name: String? = "",
    var lastName: String? = "",
    var sexo: Boolean? = false,
    var phone: String? = "",
    var email: String? = "",
    var activo: Boolean? = false,
    var dateBirth: String? = "",
)