package com.ejemplo.projectuserfirebase.Model

import com.google.gson.annotations.SerializedName

class ModelUser {
    @SerializedName("name") var _name:String = ""
    @SerializedName("lastName") var _lastName: String = ""
    @SerializedName("sexo") var _sexo: Boolean = false
    @SerializedName("phone") var _phone: String = ""
    @SerializedName("activo") var _activo: Boolean = false
    @SerializedName("dateBirth") var _dateBirth:String = ""
}
