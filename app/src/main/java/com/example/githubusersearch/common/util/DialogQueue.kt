package com.example.githubusersearch.common.util

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.githubusersearch.R
import java.util.*

class DialogQueue {
    val queue: MutableState<Queue<GenericDialogInfo>> = mutableStateOf(LinkedList())

    private fun removeHeadMessage() {
        if (queue.value.isNotEmpty()) {
            val update = queue.value
            update.remove() // 가장 처음에 들어왔던(오래된) GenericDialogInfo 제거
            queue.value = ArrayDeque()
            queue.value = update
        }
    }

    fun appendErrorMessage(title: String, description: String) {
        queue.value.offer(
            GenericDialogInfo.Builder()
                .title(title)
                .onDismiss(this::removeHeadMessage)
                .description(description)
                .positive(
                    PositiveAction(
                        positiveBtnTxt = R.string.confirm,
                        onPositiveAction = this::removeHeadMessage,
                    )
                )
                .build()
        )
    }
}