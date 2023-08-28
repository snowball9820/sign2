package com.app.sign2


import androidx.compose.ui.graphics.Path
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.sign2.ui.theme.Sign2Theme
import androidx.compose.foundation.Canvas
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.Stroke


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Sign2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SignScreen()

                }
            }
        }
    }
}

@Composable
fun SignScreen() {
    val context = LocalContext.current
    val pathState = remember { mutableStateOf(Path()) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        DrawingCanvas(pathState) {
            pathState.value = it
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = {
                StrokeManager.recognize(context)
            }) {
                Text("Recognize")
            }

            Button(onClick = {
                pathState.value.reset()
                StrokeManager.clear()
            }) {
                Text("Clear")
            }
        }
    }
}

@Composable
fun DrawingCanvas(
    path: MutableState<Path>,
    onPathChange: (Path) -> Unit
) {
    val drawModifier = Modifier.pointerInput(Unit) {
        detectTransformGestures { _, pan, _, _ ->
            path.value.lineTo(pan.x, pan.y)
            onPathChange(path.value)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .then(drawModifier)
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize(),
            onDraw = {
                drawPath(path = path.value,color= androidx.compose.ui.graphics.Color.Black, style = Stroke(width = 5f))
            }
        )
    }
}


