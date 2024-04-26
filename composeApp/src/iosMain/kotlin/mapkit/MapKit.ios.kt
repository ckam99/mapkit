package mapkit

import platform.WebKit.WKWebView


actual class MapKitMap {

    private var webView : WKWebView? = null

    actual fun addLayer() {}

    actual fun setMap(maps : Any) {
        if (maps != null){
            this.webView = maps as WKWebView
        }
    }

    actual fun zoomIn(){
        webView?.evaluateJavaScript("zoomIn();", null)
    }

    actual fun zoomOut() {
        webView?.evaluateJavaScript("zoomOut();", null)
    }
}