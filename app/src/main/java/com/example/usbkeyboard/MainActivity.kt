package com.example.usbkeyboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.usbkeyboard.ui.theme.USBKeyboardTheme
import androidx.compose.material3.Text as Text1
import android.content.Context
import java.io.OutputStreamWriter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            USBKeyboardTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Column {
                        GreetingPreview()
                        // Include the SimpleOutlinedTextFieldSample and related components
                        SimpleOutlinedTextFieldSample(context = applicationContext)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text1(
        text = "USB Android Shitty App",
        modifier = modifier
    )
}

@Composable
fun SimpleOutlinedTextFieldSample(context: Context) {

    val usbKeyboard = OutputUsbKeyboardAsRoot(OutputInterface.Language.en_US)
    var text by remember { mutableStateOf("") }
    var labelText by remember { mutableStateOf("Entered Text:") }

    OutlinedTextField(
        value = text,
        onValueChange = { newText ->
            text = newText
        },
        label = { Text1("Enter text") }
    )

    // Button to update the label text on click
    Button(
        onClick = {
            // Update the label text based on the entered text
            labelText = "Entered Text: $text"

            // Write the text to a file named "test.txt"
            writeToFile(context, "test.txt", text)
            usbKeyboard.sendText(text)
        },
        modifier = Modifier.padding(16.dp)
    ) {
        Text1("Submit")
    }

    // Button for BackSpace functionality
    Button(
        onClick = {
            usbKeyboard.sendSingleKey("backspace")
        },
        modifier = Modifier.padding(16.dp)
    ) {
        Text1("BackSpace")
    }

    // Button for Enter functionality
    Button(
        onClick = {
            usbKeyboard.sendSingleKey("enter")
        },
        modifier = Modifier.padding(16.dp)
    ) {
        Text1("Enter")
    }

    // Button for Tab functionality
    Button(
        onClick = {
            usbKeyboard.sendSingleKey("tab")
        },
        modifier = Modifier.padding(16.dp)
    ) {
        Text1("Tab")
    }
    // Label to display the updated text
    Text1(
        text = labelText,
        modifier = Modifier.padding(16.dp)
    )
}




fun writeToFile(context: Context, fileName: String, content: String) {
    try {
        val outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)
        val outputStreamWriter = OutputStreamWriter(outputStream)
        outputStreamWriter.write(content)
        outputStreamWriter.close()
        outputStream.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    USBKeyboardTheme {
        Greeting("Android")
    }
}
