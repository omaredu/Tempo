package io.github.omaredu.tempo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

private lateinit var fusedLocationClient: FusedLocationProviderClient

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val refrescar = findViewById<ImageButton>(R.id.refresh)
        val configuration = findViewById<ImageButton>(R.id.config)

        configuration.setOnClickListener {
            val intent = Intent(this, Config::class.java)
            startActivity(intent)
        }

        refrescar.setOnClickListener {
            Refresh()
            toast("Weather refreshed!")
        }

        Refresh()

    }

    private fun setupPermissions(unit: Int) {
        val permission = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i("Alert", "Permission to record denied")
            makeRequest()
        } else {
            fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        getTemp(location?.latitude, location?.longitude, unit)
                    }
        }
    }

    private fun getTemp(latitude: Double?, longitude: Double?, unit: Int) {
        val queue = Volley.newRequestQueue(this)
        if (unit == 0) {
            var url = "http://api.openweathermap.org/data/2.5/weather?lat=${latitude.toString()}&lon=${longitude.toString()}&appid=44b81114cf9fdb8f0d8bed2f3e7e6f30&units=imperial"
            val stringRequest = StringRequest(Request.Method.GET, url,
                    Response.Listener<String> { response ->

                        val respuesta: JSONObject = JSONObject(response)
                        val state: JSONObject = respuesta.getJSONObject("main")
                        val temp = state.getInt("temp")
                        val humidity = state.getInt("humidity")

                        grados.setText("${temp}")
                        humedad.setText("${humidity}%")
                    },
                    Response.ErrorListener { humedad.text = "That didn't work!" })
            queue.add(stringRequest)
        } else {
            var url = "http://api.openweathermap.org/data/2.5/weather?lat=${latitude.toString()}&lon=${longitude.toString()}&appid=44b81114cf9fdb8f0d8bed2f3e7e6f30&units=metric"
            val stringRequest = StringRequest(Request.Method.GET, url,
                    Response.Listener<String> { response ->

                        val respuesta: JSONObject = JSONObject(response)
                        val state: JSONObject = respuesta.getJSONObject("main")
                        val temp = state.getInt("temp")
                        val humidity = state.getInt("humidity")

                        grados.setText("${temp}")
                        humedad.setText("${humidity}%")
                    },
                    Response.ErrorListener { humedad.text = "That didn't work!" })
            queue.add(stringRequest)
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION),
                1)
    }

    fun Refresh(){
        val myPreference = MyPreference(this)

        val calendar = Calendar.getInstance()
        val date = calendar.getTime()
        val diaSemana = SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime())
        val mes = SimpleDateFormat("MMMM", Locale.ENGLISH).format(date.getTime())

        val dia = SimpleDateFormat("dd", Locale.getDefault()).format(Date())
        val año = SimpleDateFormat("yyyy", Locale.getDefault()).format(Date())

        fecha.setText("${diaSemana}, ${mes} ${dia.toInt()}, ${año}")

        var gradesU = myPreference.getLoginCount()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (gradesU == 1) {
            gradosTexto.setText("°C")
            setupPermissions(gradesU)
        }
        else{
            gradosTexto.setText("°F")
            setupPermissions(gradesU)
        }
    }

    fun Activity.toast(message: CharSequence, duration: Int = android.widget.Toast.LENGTH_SHORT) {
        android.widget.Toast.makeText(this, message, duration).show()
    }
}
