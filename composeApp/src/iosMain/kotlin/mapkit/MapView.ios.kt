package mapkit

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import mapkit.models.LatLng
import mapkit.models.MapKitState
import mapkit.scripts.MapKitContent
import platform.WebKit.WKWebView



@OptIn(ExperimentalForeignApi::class)
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
            wb .apply {
                this.loadHTMLString(MapKitContent.defaultHtmlContent, null)
                val maps = MapKitMap(maps = this)
                onMapLoaded(maps)
            }
        },
        modifier = modifier
    )
}
