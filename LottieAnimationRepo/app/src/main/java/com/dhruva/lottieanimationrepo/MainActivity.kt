package com.dhruva.lottieanimationrepo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dhruva.lottieanimationrepo.ui.theme.LottieAnimationRepoTheme
import com.dotlottie.dlplayer.Mode
import com.lottiefiles.dotlottie.core.compose.runtime.DotLottieController
import com.lottiefiles.dotlottie.core.compose.ui.DotLottieAnimation
import com.lottiefiles.dotlottie.core.util.DotLottieSource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LottieAnimationRepoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DotLottieMultipleAnimationsPreview(
                        modifier = Modifier.padding(
                            innerPadding
                        )
                    )
                }
            }
        }
    }
}


@Composable
fun DotLottiePlayer(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        DotLottieAnimation(
            //1st option
            //source = DotLottieSource.Url("https://lottie.host/ff6c9258-3bf2-4bf6-b029-85b0d7bd9521/D81MbBbraA.lottie"),//from url .lottie / .json
            //2nd option
            source = DotLottieSource.Asset("totoro_walk.json"), // from asset .lottie / .json
            //3rd option
////        source = DotLottieSource.Json("{"v":"4.8.0","meta":{"g":"LottieFiles .........."), // lottie json string
            //4th option
////        source = DotLottieSource.Data(ByteArray), // dotLottie data as ByteArray
            autoplay = true,
            loop = true,
            speed = 2f,
            useFrameInterpolation = false,
            playMode = Mode.FORWARD,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Text(text = "DotLottie")
    }
}

@Preview()
@Composable
fun DotLottieSpeedPreview(modifier: Modifier = Modifier) {
    val  controller = remember { DotLottieController() }
    Surface {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxSize()
        ) {
            DotLottieAnimation(
                source = DotLottieSource.Asset("totoro_walk.json"),
                autoplay = false,
                loop = true,
                controller = controller,
            )
            Button(onClick = {
                controller.setSpeed(1f)
            }) {
                controller.pause()
                Text(text = "x")
                controller.play()
            }
            Button(onClick = {
                controller.setSpeed(2f)
            }) {
                controller.pause()
                Text(text = "2x")
                controller.play()
            }
            Button(onClick = {
                controller.setSpeed(3f)
            }) {
                controller.pause()
                Text(text = "3x")
                controller.play()
            }
        }
    }
}

@Preview()
@Composable
fun DotLottieGesturePreview(modifier: Modifier = Modifier) {
    val  controller = remember { DotLottieController() }
    Surface {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxSize()
        ) {
            DotLottieAnimation(
                source = DotLottieSource.Asset("gradient_loading_j.json"),
                autoplay = false,
                loop = true,
                controller = controller,
                modifier = Modifier.pointerInput(UInt) {
                    detectTapGestures(
                        onPress = {
                            // Play animation
                            controller.play()
                            tryAwaitRelease()
                            // Pause animation
                            controller.pause()
                        },
                    )
                }
            )
        }
    }
}

@Preview()
@Composable
fun DotLottieMultipleAnimationsPreview(modifier: Modifier = Modifier) {
    val dotLottieController = remember { DotLottieController() }
    val dropdownExpand = remember { mutableStateOf(false) }
    val dropdownActive = remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize().padding(16.dp)
    )  {
        DotLottieAnimation(
            source = DotLottieSource.Asset("smiley_pack.lottie"),
            autoplay = true,
            loop = true,
            controller = dotLottieController
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp).padding(0.dp, 100.dp)) {
            Row(modifier = Modifier.padding(2.dp), verticalAlignment = Alignment.CenterVertically) {
                Button(onClick = { dropdownExpand.value = !dropdownExpand.value }) {
                    Text(text = "Animations")
                }
                DropdownMenu(expanded = dropdownExpand.value, onDismissRequest = {
                    dropdownExpand.value = false
                }) {
                    dotLottieController.manifest()?.animations?.forEach() {
                        DropdownMenuItem(text = { Text(text = it.id) }, onClick = {
                            dropdownActive.value = it.id
                            dropdownExpand.value = false
                            dotLottieController.loadAnimation(it.id)
                        })
                    }

                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LottieAnimationRepoTheme {
        DotLottieSpeedPreview()
    }
}
