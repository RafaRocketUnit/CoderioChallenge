package com.rafarocket.coderiochallenge.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.rafarocket.coderiochallenge.MainActivity
import com.rafarocket.coderiochallenge.R
import com.rafarocket.coderiochallenge.Utils
import com.rafarocket.coderiochallenge.databinding.FragmentCustomMapBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomMapFragment : Fragment(), OnMapReadyCallback, LocationListener {

    private lateinit var binding: FragmentCustomMapBinding
    private lateinit var locationManager: LocationManager

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap

    private lateinit var fireStore: FirebaseFirestore

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private var permissionActivityResult: ActivityResultLauncher<String> = registerForActivityResult(
        ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            requestLocation {
                updateLocationInFirebase(it)
            }
        } else {
            Toast.makeText(requireContext(), getString(R.string.message_must_grant_permissions), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCustomMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //Database reference firebase
        fireStore = Firebase.firestore

        //Map setting
        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        return root
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        if (Utils().checkForInternet(requireContext())) {
            if (!::locationManager.isInitialized) {
                locationManager =
                    requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            }

            googleMap.apply {
                if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ),
                        2
                    )
                }
                isMyLocationEnabled = true
                uiSettings.apply {
                    isZoomControlsEnabled = true
                    isCompassEnabled = true
                }

            }
            setMarkersFromFirebase()
            fetchPositionEveryFiveMinutes()
        } else {
            val message = getString(R.string.message_map_offline)
            val finalMessage = Html.fromHtml(message)
            Utils().createAlertDialog(
                requireContext(),
                "No internet connection",
                finalMessage
            )
        }
    }

    /**
     * This update the firebase database if the location change, even though happens the 5 minutes fetch.
     */
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onLocationChanged(location: Location) {
        updateLocationInFirebase(location)
    }

    // Private Functions

    private fun addMarker(latLan: LatLng) {
        if (::googleMap.isInitialized) {
            googleMap.addMarker(
                MarkerOptions()
                    .position(latLan)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                    .draggable(false)
                    .visible(true)
                    .title("Position: " + "${latLan.latitude} ${latLan.longitude}")
            )?.showInfoWindow()
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun updateLocationInFirebase(location: Location) {
        val locationText = "Latitude: " + location.latitude + " , Longitude: " + location.longitude
        val currentLocation = LatLng(location.latitude, location.longitude)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 20f))

        fireStore.collection("/location").add(currentLocation).addOnSuccessListener {
            notification("Was added successfully the location: ", locationText)
            setMarkersFromFirebase()
        }.addOnFailureListener {
            notification("Couldn't be added the location: ", locationText)
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun notification(message: String, locationText: String) {

        val notificationManager =
            requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        val notificationChannelId = "Coderio"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant") val notificationChannel = NotificationChannel(
                notificationChannelId,
                "Map Locations",
                NotificationManager.IMPORTANCE_MAX
            )
            // Configure the notification channel.
            notificationChannel.apply {
                description = "Markers Location"
                enableLights(true)
                lightColor = Color.RED
                vibrationPattern = longArrayOf(0, 1000, 500, 1000)
                enableVibration(true)
            }
            notificationManager?.createNotificationChannel(notificationChannel)
        }


        val contentIntent = PendingIntent.getActivity(
            requireContext(),
            9,
            Intent(requireContext(), MainActivity::class.java).apply {
              this.putExtras(Bundle().apply {
                  this.putString("navigation", "fragmentMap")
              })
            },
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder = NotificationCompat.Builder(requireContext(), notificationChannelId)
            .setContentTitle("New GPS position")
            .setContentText("$message \n $locationText")
            .setContentIntent(contentIntent)
            .setSmallIcon(R.drawable.maps)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.POST_NOTIFICATIONS, Manifest.permission.VIBRATE),
                3
            )
        } else {
            NotificationManagerCompat.from(requireContext()).notify(325, notificationBuilder)
        }
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestLocation(fetch: (location: Location) -> Unit) {
        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionActivityResult.apply {
                launch(Manifest.permission.ACCESS_FINE_LOCATION)
                launch(Manifest.permission.ACCESS_COARSE_LOCATION)
            }
        } else {
            val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
            fusedLocationClient.lastLocation.addOnCompleteListener {
                fetch(it.result)
            }
        }
    }

    private fun fetchPositionEveryFiveMinutes() {
        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post(object : Runnable {
            @RequiresApi(Build.VERSION_CODES.TIRAMISU)
            override fun run() {
                requestLocation() {
                    updateLocationInFirebase(it)
                }
                mainHandler.postDelayed(this, 1000 * 60 * 5)
            }
        })
    }

    private fun setMarkersFromFirebase() {
        fireStore.collection("/location").get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.documents}")
            val listLatLng = it.documents
            for (doc in listLatLng) {
                val latLng = LatLng(doc.data?.get("latitude") as Double, doc.data?.get("longitude") as Double)
                addMarker(latLng)
            }
        }.addOnFailureListener {
            Log.e("firebase", "Error getting data", it)
        }
    }

}