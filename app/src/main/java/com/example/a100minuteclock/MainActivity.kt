package com.example.a100minuteclock

//package com.example.hundredminuteclock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.util.Calendar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HundredMinuteClockApp()
        }
    }
}

@Composable
fun HundredMinuteClockApp() {
    var displayTime by remember { mutableStateOf("00:00:00") }
    var is24HourMode by remember { mutableStateOf(true) }

    // Update time every 100ms for smooth seconds
    LaunchedEffect(Unit) {
        while (true) {
            val now = Calendar.getInstance()
            var hour = now.get(Calendar.HOUR_OF_DAY)
            val minute = now.get(Calendar.MINUTE)
            val second = now.get(Calendar.SECOND)
            val millis = now.get(Calendar.MILLISECOND)

            // Convert real time to "100-minute clock" format
            val fakeMinutesTotal = (minute * 100.0 / 60.0)
            val fakeSecondsTotal = (second * 100.0 / 60.0) + (millis * 100.0 / (60.0 * 1000.0))

            val fakeMinute = fakeMinutesTotal.toInt()
            val fakeSecond = fakeSecondsTotal.toInt()

            val amPm = if (!is24HourMode) {
                if (hour >= 12) "PM" else "AM"
            } else ""

            if (!is24HourMode) {
                hour = hour % 12
                if (hour == 0) hour = 12
            }

            displayTime = if (is24HourMode) {
                String.format("%02d:%02d:%02d", hour, fakeMinute, fakeSecond)
            } else {
                String.format("%02d:%02d:%02d %s", hour, fakeMinute, fakeSecond, amPm)
            }

            delay(100)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = displayTime,
                color = Color.Green,
                fontSize = 64.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            androidx.compose.material3.Button(
                onClick = { is24HourMode = !is24HourMode }
            ) {
                Text(
                    text = if (is24HourMode) "Switch to 12-Hour Mode" else "Switch to 24-Hour Mode"
                )
            }
        }
    }
}



//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.enableEdgeToEdge
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import com.example.a100minuteclock.ui.theme._100MinuteClockTheme
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            _100MinuteClockTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    _100MinuteClockTheme {
//        Greeting("Android")
//    }
//}