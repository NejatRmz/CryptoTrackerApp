package com.example.cryptotrackerapp.common.workmanager

object AlertUtil {

    fun validateRegistrationInput(
        min: String,
        max: String,
    ): Boolean {
        if(min.isEmpty()) {
            return false
        }
        if(max.isEmpty()) {
            return false
        }
        if (min.toDouble()>=max.toDouble()){
            return false
        }
        return true
    }
}