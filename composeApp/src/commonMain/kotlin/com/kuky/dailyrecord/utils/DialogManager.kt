package com.kuky.dailyrecord.utils

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.Clock

sealed class DialogSpec {
    abstract val id: String // 唯一标识，用于防止重复弹出
    abstract val priority: Int  // 弹窗优先级，越大越先弹出

    data class Alert(
        override val id: String = "alert_${Clock.System.now().toEpochMilliseconds()}",
        override val priority: Int = 0,
        val title: String,
        val titleComponent: (@Composable () -> Unit)? = null,
        val text: String,
        val textComponent: (@Composable () -> Unit)? = null,
        val confirmText: String? = null,
        val onConfirm: (dialogId: String) -> Unit = {},
        val confirmButton: (@Composable () -> Unit)? = null,
        val dismissText: String? = null,
        val onDismiss: (dialogId: String) -> Unit = {},
        val dismissButton: (@Composable () -> Unit)? = null,
        val properties: DialogProperties = DialogProperties(),
        val onDismissRequest: (() -> Unit)? = null,
    ) : DialogSpec()

    data class Loading(
        override val id: String = "loading",
        override val priority: Int = 999,
        val message: String = "",
        val properties: DialogProperties = DialogProperties(),
        val onDismissRequest: (() -> Unit)? = null,
        val customContent: (@Composable () -> Unit)? = null,
    ) : DialogSpec()

    data class Custom(
        override val id: String = "custom_${Clock.System.now().toEpochMilliseconds()}",
        override val priority: Int = 0,
        val properties: DialogProperties = DialogProperties(usePlatformDefaultWidth = false),
        val onDismissRequest: (() -> Unit)? = null,
        val shape: Shape = RoundedCornerShape(0.dp),
        val enterTransition: EnterTransition = fadeIn() + scaleIn(),
        val exitTransition: ExitTransition = fadeOut() + scaleOut(),
        val alignment: Alignment = Alignment.Center,
        val content: @Composable () -> Unit,
    ) : DialogSpec()
}

data class ToastSpec(
    val id: String,
    val message: String = "",
    val background: Color = Color(0x55333333),
    val duration: Long = 2000,
    val alignment: Alignment = Alignment.Center,
    val customContent: @Composable (() -> Unit)? = null
)

/**
 * dialog 管理队列，优先级高的弹窗会优先显示
 */
class DialogManager {
    private val _dialog = MutableStateFlow<DialogSpec?>(null)
    val dialogs = _dialog.asStateFlow()

    private val _toast = MutableStateFlow<ToastSpec?>(null)
    val toast = _toast.asStateFlow()

    private val _dialogPools = mutableListOf<Pair<String, DialogSpec>>()

    suspend fun showToast(
        message: String = "", background: Color = Color(0x55333333),
        duration: Long = 2000, alignment: Alignment = Alignment.Center, customContent: @Composable (() -> Unit)? = null
    ) {
        val toastId = "toast_${Clock.System.now().toEpochMilliseconds()}"
        if (_toast.value != null) dismissToast(_toast.value!!.id)
        _toast.update { ToastSpec(toastId, message, background, duration, alignment, customContent) }
        delay(duration)
        dismissToast(toastId)
    }

    private fun dismissToast(toastId: String) {
        if (_toast.value != null && _toast.value!!.id == toastId) _toast.update { null }
    }

    fun showDialog(dialog: DialogSpec) {
        if (_dialog.value == null) {
            _dialog.update { dialog }
        } else {
            _dialogPools.add(Pair(dialog.id, dialog))
            _dialogPools.sortBy { it.second.priority }
        }
    }

    fun dismissDialog(dialogId: String) {
        _dialogPools.removeAll { it.second.id == dialogId }
        _dialog.update {
            val dialog = _dialogPools.removeFirstOrNull()
            dialog?.second
        }
    }

    fun dismissAll() {
        _dialogPools.clear()
        _dialog.update { null }
    }
}

@Composable
fun DialogContainer(dialogManager: DialogManager) {
    val dialog by dialogManager.dialogs.collectAsState()
    val toast by dialogManager.toast.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        if (dialog != null) {
            when (dialog!!) {
                is DialogSpec.Alert -> AlertDialogWrapper(dialog as DialogSpec.Alert, dialogManager)
                is DialogSpec.Loading -> LoadingWrapper(dialog as DialogSpec.Loading, dialogManager)
                is DialogSpec.Custom -> CustomDialogWrapper(dialog as DialogSpec.Custom, dialogManager)
            }
        }

        var toastModifier: Modifier = Modifier
        if (toast != null) {
            toastModifier = Modifier.padding(20.dp).align(toast!!.alignment)
        }

        AnimatedVisibility(
            visible = toast != null,
            enter = fadeIn() + slideInVertically { it }, exit = fadeOut() + slideOutVertically { it * 2 },
            modifier = toastModifier
        ) {
            if (toast == null) Box {} else toast!!.customContent ?: Box(
                modifier = Modifier.clip(RoundedCornerShape(4.dp))
                    .background(toast!!.background)
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(toast!!.message, fontSize = 16.sp, color = Color.White)
            }
        }
    }
}

@Composable
fun AlertDialogWrapper(spec: DialogSpec.Alert, manager: DialogManager) {
    AlertDialog(
        onDismissRequest = {
            manager.dismissDialog(spec.id)
            spec.onDismissRequest?.invoke()
        },
        properties = spec.properties,
        title = spec.titleComponent ?: { Text(spec.title) },
        text = spec.textComponent ?: { Text(spec.text) },
        confirmButton = spec.confirmButton ?: {
            if (spec.confirmText != null) TextButton(
                onClick = { spec.onConfirm(spec.id) }
            ) { Text(spec.confirmText) }
        },
        dismissButton = spec.dismissButton ?: {
            if (spec.dismissText != null) TextButton(
                onClick = { spec.onDismiss(spec.id) }
            ) { Text(spec.dismissText) }
        }
    )
}

@Composable
fun LoadingWrapper(spec: DialogSpec.Loading, manager: DialogManager) {
    Dialog(
        onDismissRequest = {
            manager.dismissDialog(spec.id)
            spec.onDismissRequest?.invoke()
        },
        properties = spec.properties
    ) {
        spec.customContent ?: Row {
            CircularProgressIndicator(modifier = Modifier.size(20.dp))
            Text(spec.message, modifier = Modifier.padding(start = 8.dp))
        }
    }
}

@Composable
fun CustomDialogWrapper(spec: DialogSpec.Custom, manager: DialogManager) {
    Dialog(
        onDismissRequest = {
            manager.dismissDialog(spec.id)
            spec.onDismissRequest?.invoke()
        },
        properties = spec.properties
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(align = spec.alignment)
                .clip(spec.shape)
        ) {
            AnimatedVisibility(visible = true, enter = spec.enterTransition, exit = spec.exitTransition) {
                spec.content()
            }
        }
    }
}