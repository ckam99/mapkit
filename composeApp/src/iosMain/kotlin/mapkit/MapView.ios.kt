package mapkit

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.readValue
import mapkit.models.LatLng
import mapkit.models.MapKitState
import mapkit.scripts.MapKitContent
import org.jetbrains.compose.resources.ExperimentalResourceApi
import platform.CoreGraphics.CGRectZero
import platform.WebKit.WKWebView
import platform.WebKit.WKWebViewConfiguration
import platform.WebKit.javaScriptEnabled


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


     val navigationDelegate = remember { WKNavigationDelegate(state,) }

    UIKitView(
        factory = {

            val config = WKWebViewConfiguration().apply {
                                allowsInlineMediaPlayback = true
                                defaultWebpagePreferences.allowsContentJavaScript = true
                                preferences.apply {
//                                    setValue(state.webSettings.allowFileAccessFromFileURLs, forKey = "allowFileAccessFromFileURLs")
                                    javaScriptEnabled = true
                                }
                            }

        val wb =  WKWebView( frame = CGRectZero.readValue(), configuration = config,)
            wb.apply {
                this.loadHTMLString(MapKitContent.defaultHtmlContent, null)
                this.navigationDelegate = navigationDelegate
                val maps = MapKitMap()
                onMapLoaded(maps)
                maps.setMap(this)
                state.bind(maps)
                state.setCameraState(style, zoom, center)

//                val centerStr = "[${center.longitude},${center.latitude}]"
//                wb.evaluateJavaScript("LoadMapsKit('$style',$centerStr, $zoom);", null)
//                wb.evaluateJavaScript( "addMarker(5.33125, -4.02375);", null)
            }

        },
        modifier = modifier
    )
}
