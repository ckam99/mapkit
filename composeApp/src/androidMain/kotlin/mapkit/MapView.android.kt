package mapkit

import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import mapkit.models.LatLng
import mapkit.models.MapKitState
import mapkit.scripts.MapKitContent


@Composable
actual fun MapView(
    modifier: Modifier,
    center: LatLng,
    style: String,
    zoom: Double,
    state: MapKitState,
    onMapLoaded: (maps: MapKitMap) -> Unit,
){
    AndroidView(
        factory = { context ->
                WebView(context).apply {
                    layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                    )
                    val maps = MapKitMap(maps = this)
                    this.settings.javaScriptEnabled = true
                    webViewClient = object : WebViewClient(){
                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                          onMapLoaded(maps)
                        }
                    }
                    loadDataWithBaseURL(null, MapKitContent.defaultHtmlContent, "text/html", "UTF-8", null)
                    
                }
            },
            modifier = modifier,
        )
}
