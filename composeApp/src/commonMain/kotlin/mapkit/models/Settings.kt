package mapkit.models

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

data class MapKitSettings(
    var enableCache: Boolean = false
)

data class MapKitState(
    val settings: MapKitSettings
)

@Composable
fun rememberMapKitState(
    enableCache: Boolean = false
): MapKitState = remember {
    MapKitState(settings = MapKitSettings(enableCache = enableCache))
}
