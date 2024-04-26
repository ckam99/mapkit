package mapkit.scripts


internal const val mapKitJavascriptContent = """
let map = new maplibregl.Map({
    container: "map_canvas",
    style: "http://192.168.0.116:8080/styles/ls3/style.json",
    center: [-4.02375, 5.33125],
    zoom: 16
});
"""