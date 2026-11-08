package com.example.sprint7example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.sprint7example.ui.theme.Sprint7ExampleTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Sprint7ExampleTheme {
                FloatButtonBottomSheetScreen()
            }
        }
    }
}

@Composable
fun FloatButtonBottomSheetScreen(modifier: Modifier = Modifier)
{
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }
    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        FloatButtonExample(
            modifier = Modifier.padding(innerPadding),
            callback = { showBottomSheet = true }
        )
        BottomSheetExample(
            isShowPanel = showBottomSheet,
            onDismissRequest = { showBottomSheet  = false },
            content = "Это панель BottomSheet"
        )
    }
}

@Composable
fun FloatButtonExample(modifier: Modifier, callback: () -> Unit)
{
    Box(modifier = modifier.fillMaxSize()) {
        FloatingActionButton(
            modifier = Modifier.padding(32.dp).align(Alignment.BottomEnd),
            onClick = callback,
            containerColor = Color.Gray,
            contentColor = Color.White,
            shape = CircleShape
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Это FloatingActionButton"
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetExample(modifier: Modifier = Modifier, isShowPanel: Boolean, onDismissRequest: () -> Unit, content: String)
{
    val sheetState = rememberModalBottomSheetState()

    if (isShowPanel)
    {
        val scope = rememberCoroutineScope()

        ModalBottomSheet(
            onDismissRequest = {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible)
                        onDismissRequest()
                }
            },
            sheetState = sheetState
        ) {
            Column(
                modifier = modifier.fillMaxWidth().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = content,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Icon(
                    modifier = Modifier.size(32.dp).padding(top = 16.dp),
                    imageVector = Icons.Default.Home,
                    contentDescription = "Icon"
                )
            }
        }
    }
}