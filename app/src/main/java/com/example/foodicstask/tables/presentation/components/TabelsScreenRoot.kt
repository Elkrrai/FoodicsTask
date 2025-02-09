package com.example.foodicstask.tables.presentation.components

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.foodicstask.core.presentation.util.ObserveAsEvents
import com.example.foodicstask.core.presentation.util.toString
import com.example.foodicstask.tables.presentation.TablesEvent
import com.example.foodicstask.tables.presentation.TablesViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun TablesScreenRoot(
    viewModel: TablesViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    ObserveAsEvents(events = viewModel.events) { event ->
        when(event) {
            is TablesEvent.Error -> {
                Toast.makeText(
                    context,
                    event.error.toString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    val state = viewModel.state.collectAsStateWithLifecycle()

    TablesScreen(
        state = state.value,
        modifier = modifier,
        onAction = viewModel::onAction
    )
}
