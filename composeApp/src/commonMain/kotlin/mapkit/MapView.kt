package mapkit

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import mapkit.models.LatLng
import mapkit.models.MapKitSettings
import mapkit.models.MapKitState


@Composable
expect fun MapView(
    modifier: Modifier = Modifier,
    center: LatLng,
    style: String,
    zoom: Double = 6.0,
    state: MapKitState,
    onMapLoaded: (maps: MapKitMap) -> Unit,
)
