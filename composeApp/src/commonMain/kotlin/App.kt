import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mapkit.MapKitMap
import mapkit.MapView
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import mapkit.composeapp.generated.resources.Res
import mapkit.composeapp.generated.resources.compose_multiplatform
import mapkit.models.LatLng
import mapkit.models.rememberMapKitState


@Composable
@Preview
fun App() {

    var maps : MapKitMap? by remember {
        mutableStateOf(null)
    }

    MaterialTheme {
        val style = "http://192.168.0.116:8080/styles/ls3/style.json"
        val styleDark = "http://192.168.0.116:8080/styles/ds1/style.json"
    MapView(
        modifier = Modifier.fillMaxSize(),
        state = rememberMapKitState(),
        center = LatLng(5.33125, -4.02375),
        zoom = 14.0,
        style = if (isSystemInDarkTheme() ) styleDark else style
    ){
        maps = it
    }

        Box(
                            contentAlignment = Alignment.CenterEnd,
                            modifier = Modifier.padding(10.dp).fillMaxSize()
                        ) {
                            Column {
                                Button(
                                    onClick = { maps?.zoomIn() },
//                                    colors = IconButtonDefaults.filledIconButtonColors(),
                                ) {
                                Text("+")
                                }
                                Spacer(modifier = Modifier.height(5.dp))
                                Button(
//                                    colors = IconButtonDefaults.filledIconButtonColors(),
                                    onClick = { maps?.zoomOut() }) {
                                    Text("-")
                                }
                            }
                        }
    }
}