package mapkit

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import mapkit.models.LatLng
import mapkit.models.MapKitState
import mapkit.scripts.MapKitContent
import org.jetbrains.compose.resources.ExperimentalResourceApi
import platform.WebKit.WKWebView



@OptIn(ExperimentalForeignApi::class, ExperimentalResourceApi::class)
@Composable
actual fun MapView(
    modifier: Modifier,
    center: LatLng,
    style: String,
    zoom: Double,
    state: MapKitState,
    onMapLoaded: (maps: MapKitMap) -> Unit,
    ){

    UIKitView(
        factory = {
        val wb =  WKWebView()
            wb.apply {
                this.loadHTMLString(MapKitContent.defaultHtmlContent, null)
                val maps = MapKitMap()
                onMapLoaded(maps)
                maps.setMap(this)
                val centerStr = "[${center.longitude},${center.latitude}]"
                wb.evaluateJavaScript("LoadMapsKit('$style',$centerStr, $zoom);", null)
                wb.evaluateJavaScript( "addMarker(5.33125, -4.02375);", null)
            }

        },
        modifier = modifier
    )
}
