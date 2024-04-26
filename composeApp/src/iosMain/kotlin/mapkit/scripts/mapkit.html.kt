package mapkit.scripts

import mapkit.scripts.maplibre.maplibreLibCss
import mapkit.scripts.maplibre.maplibreLibJavascript

internal val mapKitHtmlContent =  """
<!DOCTYPE html>
<html>
    <head>
        <style>
        ${maplibreLibCss.trimIndent()}
        ${mapKitCssContent.trimIndent()}
        </style>
    </head>
    <body>
        <div id="map_canvas"></div>
    <script>
      ${maplibreLibJavascript.trimIndent()}
      ${mapKitJavascriptContent.trimIndent()}
    </script>
    </body>
</html>
""".trimIndent()