package mapkit

import android.webkit.WebView

actual class MapKitMap{

    private var maps : WebView? = null

    actual fun addLayer() {
        
    }

    actual fun setMap(maps : Any) {
        this.maps = maps as WebView
    }

    actual fun zoomIn(){
        maps?.loadUrl("javascript:zoomIn();")
    }
    actual fun zoomOut() {
        maps?.loadUrl("javascript:zoomOut();")
    }
}