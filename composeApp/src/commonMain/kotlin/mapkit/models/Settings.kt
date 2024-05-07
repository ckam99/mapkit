package mapkit.models

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import mapkit.MapKitMap

data class MapKitSettings(
    var enableCache: Boolean = false
)

data class MapKitState(
    val settings: MapKitSettings,
){
  private  var _isLoaded: Boolean = false
  val isLoaded : Boolean
      get () = _isLoaded

    private  var _map: MapKitMap? = null
    val map : MapKitMap?
            get () = _map

    private  var _zoom: Double = 9.0
    val zoom : Double get () = _zoom

    private  var _center: LatLng = LatLng(0.0,0.0)
    val center : LatLng get () = _center
    private  var _style: String = ""
    val style : String get () = _style

    fun setCameraState(style: String, zoom: Double, center: LatLng ){
        this._center = center
        this._style = style
        this._zoom = zoom
    }

    internal fun bind(map: MapKitMap){
        _map = map
    }

    internal fun setLoaded(loaded: Boolean){
        _isLoaded = loaded
    }
}

@Composable
fun rememberMapKitState(
    enableCache: Boolean = false
): MapKitState = remember {
    MapKitState(settings = MapKitSettings(enableCache = enableCache))
}
