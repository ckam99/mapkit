function parseArrayString(value) {
  if (typeof value === 'string' && value.startsWith('[') && value.endsWith(']')) {
    try {
      return JSON.parse(value);
    } catch (error) {
      console.error('Error parsing array string:', error);
      return value;
    }
  }
  return value;
}

function normalizeLayer(layer) {
  const layerObject = JSON.parse(layer);
  if (layerObject.layout) {
    for (const key in layerObject.layout) {
      layerObject.layout[key] = parseArrayString(layerObject.layout[key]);
    }
    // if (layerObject.layout['text-offset']) {
    //   layerObject.layout['text-offset'] = JSON.parse(layerObject.layout['text-offset']);
    // }
  }

  return layerObject
}

async function loadStyle(styleUrl) {
  let data
  let mapCacheStyle = localStorage.getItem('mapCacheStyle');
  if (mapCacheStyle) {
    data = JSON.parse(mapCacheStyle)
  } else {
    const response = await fetch(styleUrl);
    data = await response.json();
    localStorage.setItem('mapCacheStyle', JSON.stringify(data));
  }
  return data
}

var map;

/**
* Initialize map
*
* @param {string} style - style content or URL
* @param {[number,number]} center - initial camera position
* @param {string} zoom - zoom level
*/
window.LoadMapsKit = async function (style, center, zoom) {
  //  const defaultStyle = await loadStyle();
  map = new maplibregl.Map({
    container: "map_canvas",
    style: style, // 'http://192.168.0.116:8080/styles/v3/style.json',
    center: center, //[-4.02375, 5.33125]
    zoom: zoom, // 12
  });
  map.on("load", function () {
    console.log("maplibre loaded");
    MapKit.handleLoad();
  });
  /*
  map.on('styleimagemissing', function(e) {
    if (e.id === 'chat-marker') {
        // Assuming 'chatMarkerImage' is a URL or a data URL to your image
        map.loadImage("https://upload.wikimedia.org/wikipedia/commons/7/7c/201408_cat.png", function(error, image) {
            if (error) throw error;
            map.addImage(e.id, image);
        });
    }
});
*/
  map.on("click", function (e) {
    // MapKit.handleMapClick(`{"latitude": ${e.lngLat.lat}, "longitude": ${e.lngLat.lng}}`)
    console.log(e.lngLat);
    const features = e.target.queryRenderedFeatures(e.point);
    if (features.length > 0) {
      MapKit.handleMapFeatureClick(JSON.stringify(features[0]));
    }
    MapKit.handleMapClick(JSON.stringify(e.lngLat));
    console.log(JSON.stringify(features));
  });
};

/**
* Set map style
*
* @param {string} style - style content or URL
*/
window.setMapStyle = function (style) {
  map?.setStyle(style);
};
/**
* Zoom camera out.
*
* @param {number} lat - latitude
* @param {number} lng - longitude
*/
window.addMarker = function (lat, lng) {
  if (!map) return;
  new maplibregl.Marker().setLngLat([lng, lat]).addTo(map);
};
/**
* Zoom camera in.
*
*/
window.zoomIn = function () {
  map?.zoomIn();
};
/**
* Zoom camera out.
*
*/
window.zoomOut = function () {
  map?.zoomOut();
};

/**
* Add source.
*
* @param {string} sourceId - Source ID
* @param {string} data - source json data
*/
window.addSource = function (sourceId, data) {
  try {
    map?.addSource(sourceId, JSON.parse(data));
  } catch (e) {
    MapKit.handleError("addSource", JSON.stringify(e));
  }
};
/**
* Add layer.
*
* @param {string} data - layer json data
*/
window.addLayer = function (data) {
  try {
    map?.addLayer(normalizeLayer(data));
  } catch (e) {
    MapKit.handleError("addLayer", JSON.stringify(e));
  }
};
/**
* Add image by URL.
*
* @param {string} imageId - The iamge ID to calculate the factorial of.
* @param {string} imageUrl - Url of image.
// * @returns {number} Url of image.
*/
window.addImage = async function (imageId, imageUrl) {
  try {
    const image = await map?.loadImage(imageUrl)
    map?.addImage(imageId, image.data);
  } catch (e) {
    MapKit.handleError("addImageUrl", JSON.stringify(e));
  }
};

/**
* Add image using base64 content.
*
* @param {string} layer - layer json data
* @param {string} imageId - The iamge ID to calculate the factorial of.
* @param {string} imageUrl - The icon url.
*/
window.addSymbolLayer = async function (layer, imageId, imageUrl) {
  try {
    const image = await map?.loadImage(imageUrl)
    map?.addImage(imageId, image.data);
    map?.addLayer(normalizeLayer(layer));
  } catch (e) {
    MapKit.handleError("addSymbolLayer", JSON.stringify(e));
  }
};


/**
* Get source.
*
* @param {string} sourceId - The source ID
*/
window.getSource = function (sourceId) {
  try {
    const source = map?.getSource(sourceId);
    if (source) {
      const data = source?.serialize()
      console.log("source found: ", source?.type, source?.id, source?.minzoom, source?.maxzoom, JSON.stringify(data))
      return data
    }
  } catch (e) {
    console.log("Error getSource:", e);
    MapKit.handleError("getSource", JSON.stringify(e));
  }
};


/**
* Get layer.
*
* @param {string} sourceId - The layer ID
*/
window.getLayer = function (layerId) {
  try {
    const layer = map?.getLayer(layerId);
    if (layer) {
      const datab = {
        id: layer?.id,
        paint: layer?.paint,
        layout: layer?.layout,
        maxzoom: layer?.maxzoom,
        minzoom: layer?.minzoom,
        source: layer?.source,
        sourceLayer: layer?.sourceLayer,
        type: layer?.type,
      }

      const data = layer?.serialize()
      console.log("layer brut: ", JSON.stringify(datab))
      console.log("layer found: ", JSON.stringify(data))
      return data
    }
  } catch (e) {
    console.log("Error getLayer:", e);
    MapKit.handleError("getLayer", JSON.stringify(e));
  }
};


/**
* Check if image exists.
*
* @param {string} id - The image ID
*/
window.hasImage = function (id) {
  try { return map?.hasImage(id); } catch (e) {
    MapKit.handleError("hasImage", JSON.stringify(e));
  }
};

/**
* Remove image.
*
* @param {string} id - The image ID
*/
window.removeImage = function (id) {
  try { map?.removeImage(id); } catch (e) {
    MapKit.handleError("removeImage", JSON.stringify(e));
  }
};

/**
* Remove layer.
*
* @param {string} id - layer ID
*/
window.removeLayer = function (id) {
  try { map?.removeLayer(id); } catch (e) {
    MapKit.handleError("removeLayer", JSON.stringify(e));
  }
};

/**
* Remove source.
*
* @param {string} id - source ID
*/
window.removeSource = function (id) {
  try { map?.removeSource(id); } catch (e) {
    MapKit.handleError("removeSource", JSON.stringify(e));
  }
};



/**
* Set layer property.
*
* @param {string} layerId - layer id
* @param {string} properties - properties
* @param {string} propertyType - Property type
*/
window.setLayerProperties = async function (layerId, properties, propertyType) {
  const property = JSON.parse(properties) 
  try {
    if (propertyType === "paint") {
      for (const name in property) {
        map?.setPaintProperty(layerId, name, parseArrayString(property[name]))
      }
    } else {
      for (const name in property) {
        map?.setLayoutProperty(layerId, name, parseArrayString(property[name]))
      }
    }
  } catch (e) {
    MapKit.handleError("setLayerProperty", e?.message);
  }
};
// /**
// * Set layout property.
// *
// * @param {string} layerId - layer id
// * @param {string} property - property
// * @param {string} value - property value.
// * @param {string} propertyType - Property type
// */
window.setLayerProperty = async function (layerId, property, value, propertyType) {
  try {
    if (propertyType === "paint") {
      map?.setPaintProperty(layerId, property, parseArrayString(value))
    } else {
      map?.setLayoutProperty(layerId, property, parseArrayString(value))
    }
  } catch (e) {
    MapKit.handleError("setLayerProperty", e?.message);
  }
};
/**
* Update source data.
*
* @param {string} id - layer id
* @param {string} data - feature data
*/
window.updateSource = function (id, data) {
  try {
    const source = map?.getSource(id)
    if (source) {
      source.setData(JSON.parse(data))
    }
  } catch (e) {
    MapKit.handleError("updateSource",  e?.message);
  }
}
/**
* Update source coordinates.
*
* @param {string} id - layer id
* @param {string} coordinates - feature coordinates
*/
window.updateSourceCoord = function (id, coordinates) {
  try {
    const source = map?.getSource(id)
    if (source) {
      const data = {
        ...source._data,
        geometry: {
          ...source._data.geometry,
          coordinates: JSON.parse(coordinates)
        }
      }
      source.setData(data)
    }
  } catch (e) {
    MapKit.handleError("updateSourceCoord", e?.message);
  }
}
/**
* Update source geometry.
*
* @param {string} id - layer id
* @param {string} geometry - feature geometry
*/
window.updateSourceGeometry = function (id, geometry) {
  console.log("updateSourceGeometry", id, geometry);
  try {
    const source = map?.getSource(id)
    if (source) {
      const data = {
        ...source._data,
        geometry: JSON.parse(geometry)
      }
       console.log("updateSourceGeometry", id, JSON.stringify(data));
       source.setData(data)
    }
  } catch (e) {
    MapKit.handleError("updateSourceGeometry", e?.message);
  }
}

