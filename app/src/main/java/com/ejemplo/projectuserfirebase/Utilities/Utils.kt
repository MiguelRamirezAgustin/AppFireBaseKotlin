package com.ejemplo.projectuserfirebase.Utilities

import android.util.Patterns
import java.util.regex.Pattern

class Utils {

    companion object{
        fun validateEmal(email:String):Boolean{
            return  Patterns.EMAIL_ADDRESS.toRegex().matches(email)
        }
    }
}