package com.example.dvt.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dvt.R
import com.example.dvt.helper_class.FormatterHelper
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.ItemizedOverlay
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.OverlayItem
import java.util.*

class Maps : AppCompatActivity() {

    private lateinit var map: MapView
    private var formatterHelper = FormatterHelper()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        initMap()
    }

    private fun initMap() {

        val latitudeKey = getString(R.string.latitude)
        val longitudeKey = getString(R.string.longitude)

        val latitude = formatterHelper.retrieveSharedPreference(this, latitudeKey)
        val longitude = formatterHelper.retrieveSharedPreference(this, longitudeKey)

        if (latitude != null && longitude != null){

            val geoPoint = GeoPoint(latitude.toDouble(), longitude.toDouble())

            map = findViewById(R.id.map)
            map.setTileSource(TileSourceFactory.OpenTopo)
            map.setMultiTouchControls(true)
            map.controller.setZoom(4.0)
            map.setMaxZoomLevel(null)

            val mapController = map.controller as MapController
            mapController.setZoom(18.5)
            mapController.setCenter(geoPoint)

            //        mapController.animateTo(geoPoint, 16.5, 9000L);
            val overlayItem = OverlayItem("Lat", "Long", geoPoint)
            val markerDrawable = this.resources.getDrawable(R.drawable.ic_action_location)
            overlayItem.setMarker(markerDrawable)
            val overlayItemArrayList = ArrayList<OverlayItem>()
            overlayItemArrayList.add(overlayItem)
            val locationOverlay: ItemizedOverlay<OverlayItem> = ItemizedIconOverlay(this,
                overlayItemArrayList, null)
            map.overlays.add(locationOverlay)

            val coordinateDetails = """
            latitude: $latitude
            longitude: $longitude
            """.trimIndent()

            val marker = Marker(map)
            marker.icon = markerDrawable
            marker.position = geoPoint
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            marker.title = coordinateDetails
            marker.setPanToView(true)
            marker.isDraggable = true
            map.overlays.add(marker)
            map.invalidate()

        }




    }

}