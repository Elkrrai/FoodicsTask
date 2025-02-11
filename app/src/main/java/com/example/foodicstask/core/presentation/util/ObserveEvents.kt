package com.example.foodicstask.core.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * Observes a [Flow] of events and executes an action for each emitted event.
 *
 * This composable function is designed to observe a [Flow] of events within the context of
 * a Compose UI and react to each event by executing the provided [onEvent] lambda.
 * It uses [LaunchedEffect] and [repeatOnLifecycle] to ensure that the flow is collected
 * only when the lifecycle is in the [Lifecycle.State.STARTED] state, preventing unnecessary
 * resource consumption when the UI is not visible.
 *
 * @param events The [Flow] of events to observe.
 * @param key1 An optional key to restart the effect when it changes.
 * @param key2 An optional key to restart the effect when it changes.
 * @param onEvent The lambda to execute when a new event is emitted by the [Flow].
 *                This lambda will be executed on the main thread.
 * @param T The type of the events emitted by the [Flow].
 */
@Composable
fun <T> ObserveEvents(
    events: Flow<T>,
    key1: Any? = null,
    key2: Any? = null,
    onEvent: (T) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner.lifecycle, key1, key2) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                events.collect(onEvent)
            }
        }
    }
}
