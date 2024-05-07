package mapkit


import mapkit.models.MapKitState
import platform.Foundation.NSError
import platform.WebKit.WKNavigation
import platform.WebKit.WKNavigationDelegateProtocol
import platform.WebKit.WKWebView
import platform.darwin.NSObject

/**
* Created By Kevin Zou On 2023/9/13
*/

/**
* Navigation delegate for the WKWebView
*/
@Suppress("CONFLICTING_OVERLOADS")
class WKNavigationDelegate(
    private val state: MapKitState,
) : NSObject(), WKNavigationDelegateProtocol {
    /**
    * Called when the web view begins to receive web content.
    */
    override fun webView(
        webView: WKWebView,
        didStartProvisionalNavigation: WKNavigation?,
    ) {
        
    }

    /**
    * Called when the web view receives a server redirect.
    */
    override fun webView(
        webView: WKWebView,
        didCommitNavigation: WKNavigation?,
    ) {
       // val zoomLevel = 100
       // @Suppress("ktlint:standard:max-line-length")
       // val script = "var meta = document.createElement('meta');meta.setAttribute('name', 'viewport');meta.setAttribute('content', 'width=device-width, initial-scale=${zoomLevel}, maximum-scale=10.0, minimum-scale=0.1,user-scalable=$supportZoom');document.getElementsByTagName('head')[0].appendChild(meta);"
       // webView.evaluateJavaScript(script) { _, _ -> }
//        KLogger.info { "didCommitNavigation" }
    }

    /**
    * Called when the web view finishes loading.
    */
    override fun webView(
        webView: WKWebView,
        didFinishNavigation: WKNavigation?,
    ) {
        state.setLoaded(true)
        val m = MapKitMap()
        m.setMap(webView)
        state.bind(m)
        val centerStr = "[${state.center.longitude},${state.center.latitude}]"
        webView.evaluateJavaScript("LoadMapsKit('${state.style}',$centerStr, ${state.zoom});", null)
        webView.evaluateJavaScript( "addMarker(5.33125, -4.02375);", null)
    }

    /**
    * Called when the web view fails to load content.
    */
    override fun webView(
        webView: WKWebView,
        didFailProvisionalNavigation: WKNavigation?,
        withError: NSError,
    ) {
        
    }
}