package co.com.emegonza.cutnow.contracts

import org.json.simple.JSONObject

interface FirebaseDelegate {
    fun onUpdateBarberData(barber: JSONObject)
}