import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    MaterialTheme {
        val style = "http://192.168.0.116:8080/styles/ls3/style.json"
        val styleDark = "http://192.168.0.116:8080/styles/ds1/style.json"
      MapView(
          modifier = Modifier.fillMaxSize(),
          state = rememberMapKitState(),
          center = LatLng(5.33125, -4.02375),
          style = if (isSystemInDarkTheme() ) styleDark else style
      ){
          
      }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun GreetScreen(){
    var showContent by remember { mutableStateOf(false) }
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = { showContent = !showContent }) {
                    Text("Click me!")
                }
                AnimatedVisibility(showContent) {
                    val greeting = remember { Greeting().greet() }
                    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(painterResource(Res.drawable.compose_multiplatform), null)
                        Text("Compose: $greeting")
                        Text("HHFH")
                    }
                }
            }
}