package io.github.omaredu.tempo

import android.content.pm.PackageManager
import android.graphics.Typeface
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.util.JsonReader
import android.util.Log
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.json.JSONArray
import org.json.JSONObject
import java.io.Reader
import java.lang.reflect.Array
import java.text.SimpleDateFormat
import java.util.*

private lateinit var fusedLocationClient: FusedLocationProviderClient

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gradostxt = findViewById<TextView>(R.id.gradosTexto)
        val grados = findViewById<TextView>(R.id.grados)
        val fecha = findViewById<TextView>(R.id.fecha)
        val humedadtxt = findViewById<TextView>(R.id.humedadTexto)
        val humedad = findViewById<TextView>(R.id.humedad)

        val productSans : Typeface? = ResourcesCompat.getFont(this, R.font.productsans)
        gradostxt.typeface = productSans
        grados.typeface = productSans
        fecha.typeface = productSans
        humedadtxt.typeface = productSans
        humedad.typeface = productSans


        val calendar = Calendar.getInstance()
        val date = calendar.getTime()
        val diaSemana = SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime())
        val mes = SimpleDateFormat("MMMM", Locale.ENGLISH).format(date.getTime())

        val dia = SimpleDateFormat("dd", Locale.getDefault()).format(Date())
        val año = SimpleDateFormat("yyyy", Locale.getDefault()).format(Date())

        fecha.setText("${diaSemana}, ${mes} ${dia.toInt()}, ${año}")


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        setupPermissions()
        setupPermissions()
    }

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i("Alert", "Permission to record denied")
            makeRequest()
        }
        else {
            fusedLocationClient.lastLocation
                    .addOnSuccessListener { location : Location? ->
                        getTemp(location?.latitude, location?.longitude)
                    }
        }
    }

    private fun getTemp(latitude : Double?, longitude : Double?) {
        val grados = findViewById<TextView>(R.id.grados)
        val humedad = findViewById<TextView>(R.id.humedad)
        val queue = Volley.newRequestQueue(this)
        val url = "http://api.openweathermap.org/data/2.5/weather?lat=${latitude.toString()}&lon=${longitude.toString()}&appid=44b81114cf9fdb8f0d8bed2f3e7e6f30&units=metric"

        val stringRequest = StringRequest(Request.Method.GET, url,
                Response.Listener<String> { response ->

                    val respuesta : JSONObject = JSONObject(response)
                    //val respuestaArray : JSONArray = respuesta.getJSONArray("main")
                    //val estado : JSONObject = respuestaArray.getJSONObject(1)
                    val state :JSONObject = respuesta.getJSONObject("main")
                    val temp = state.getInt("temp")
                    val humidity = state.getInt("humidity")

                    grados.setText("${temp}")
                    humedad.setText("${humidity}%")
                },
                Response.ErrorListener { humedad.text = "That didn't work!" })
        queue.add(stringRequest)
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION),
                1)
    }
}
