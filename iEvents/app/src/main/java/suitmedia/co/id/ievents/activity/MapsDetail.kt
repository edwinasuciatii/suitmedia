package suitmedia.co.id.ievents.activity

import android.annotation.SuppressLint
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import suitmedia.co.id.ievents.R
import suitmedia.co.id.ievents.classes.ModelListMapping
import suitmedia.co.id.ievents.classes.PropertyStatusBar
import suitmedia.co.id.ievents.classes.PropertyVector
import suitmedia.co.id.ievents.databinding.MapsDetailBinding

class MapsDetail : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: MapsDetailBinding
    private var googleMap: GoogleMap? = null
    private var listMapping = ArrayList<ModelListMapping>()
    private lateinit var mapFragment: SupportMapFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MapsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initContent()
    }

    private fun initContent() {
        PropertyStatusBar().changeColor(R.color.orange,this)
        listMapping.clear()
        listMapping.add(
            ModelListMapping(5.548290, 95.323753,"Card Title", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", "15 Jan 2021", "9.00 AM")
        )
        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap?) {
        googleMap = p0
        googleMap!!.clear()
        val markerList = ArrayList<LatLng>()
        for (i in 0 until listMapping.size) {
            val location = LatLng(listMapping[i].latitude, listMapping[i].longitude)
            val drawable: Int = R.drawable.ic_marker_unselected
            googleMap!!.addMarker(
                MarkerOptions()
                    .position(location)
                    .icon(drawable.let { PropertyVector().vectorToBitmap(this, it) })
                    .snippet(listMapping[i].title + "^" + listMapping[i].desc + "^" + listMapping[i].date + "^"+ listMapping[i].time)
            )
            markerList.add(location)
        }
        googleMap!!.setOnMarkerClickListener { marker ->
            val dX = resources.getDimensionPixelSize(R.dimen.map_dx)
            val dY = resources.getDimensionPixelSize(R.dimen.map_dy)
            val projection: Projection = googleMap!!.projection
            val markerPoint: Point = projection.toScreenLocation(
                marker.position
            )
            markerPoint.offset(dX, dY)
            val newLatLng = projection.fromScreenLocation(markerPoint)
            googleMap!!.animateCamera(CameraUpdateFactory.newLatLng(newLatLng))
            marker.showInfoWindow()
            true
        }
        googleMap!!.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
            @SuppressLint("InflateParams")
            override fun getInfoWindow(marker: Marker): View {
                val v: View = layoutInflater.inflate(R.layout.events_snippet, null)
                val title = v.findViewById<View>(suitmedia.co.id.ievents.R.id.title) as TextView
                val desc = v.findViewById<View>(suitmedia.co.id.ievents.R.id.desc) as TextView
                val date = v.findViewById<View>(suitmedia.co.id.ievents.R.id.date) as TextView
                val time = v.findViewById<View>(suitmedia.co.id.ievents.R.id.time) as TextView
                val strMarker = marker.snippet?.split("^")?.toTypedArray()
                val txtTitle: String = strMarker!![0]
                val txtDesc: String = strMarker[1]
                val txtDate: String = strMarker[2]
                val txtTime: String = strMarker[3]
                title.text = txtTitle
                desc.text = txtDesc
                date.text = txtDate
                time.text = txtTime
                return v
            }

            // Defines the contents of the InfoWindow
            @SuppressLint("InflateParams")
            override fun getInfoContents(marker: Marker): View? {
                return null
            }
        })
        val builder = LatLngBounds.Builder()
        for (i in 0 until markerList.size) {
            builder.include(markerList[i])
        }
        val bounds = builder.build()
        val width = resources.displayMetrics.widthPixels
        val height = resources.displayMetrics.heightPixels
        val padding = (width * 0.20).toInt()
        val cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding)
        googleMap!!.animateCamera(cu)
    }
}