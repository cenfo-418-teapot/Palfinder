package com.example.palfinder.backend.strapi

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson

object Strapi {
    private const val endpoint = "http://ec2-54-211-71-104.compute-1.amazonaws.com:1337"
    private const val TAG = "Strapi"
    private lateinit var requestQueue: RequestQueue
    private val _authStrings = MutableLiveData<HashMap<String, Any>>()

    fun getSingleType(context: Context, type: String) {
        this.requestQueue = Volley.newRequestQueue(context);
        val url = "$endpoint/$type"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                Log.i(TAG, "Response: %s".format(response.toString()))
                val parsedResult =
                    Gson().fromJson<HashMap<String, Any>>(response.toString(), HashMap::class.java)
                this._authStrings.postValue(parsedResult)
            },
            { error ->
                Log.e(TAG, "Failed to error", error)
            }
        )
        this.requestQueue.add(jsonObjectRequest)
    }
}