package mapkit.scripts


object MapKitContent {
   val defaultHtmlContent = mapKitDefaultHtmlContent
}

val mapKitDefaultHtmlContent = """
<!DOCTYPE html>
<html>
    <head>
        <link rel='stylesheet' href='https://unpkg.com/maplibre-gl@4.1.3/dist/maplibre-gl.css' />
        <script src="https://unpkg.com/maplibre-gl@4.1.3/dist/maplibre-gl.js"></script>
        <style>
            html,
            body,
            #map_canvas {
                height: 100%;
                margin: 0;
                padding: 0;
            }
        </style>
    </head>
    <body>
        <div id="map_canvas"></div>
    <script>
        let map = new maplibregl.Map({
                    container: "map_canvas",
                    style: "http://192.168.0.116:8080/styles/ds1/style.json",
                    center: [-4.02375, 5.33125],
                    zoom: 16
                });
    </script>
    </body>
</html>
""".trimIndent()