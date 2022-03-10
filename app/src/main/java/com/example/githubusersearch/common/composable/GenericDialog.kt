package com.example.githubusersearch.common.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.githubusersearch.common.util.GenericDialogInfo
import com.example.githubusersearch.common.util.NegativeAction
import com.example.githubusersearch.common.util.PositiveAction
import java.util.*


@Composable
fun GenericDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    title: String,
    description: String? = null,
    positiveAction: PositiveAction? = null,
    negativeAction: NegativeAction? = null
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            description?.let {
                Text(description)
            }
        },
        buttons = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(9.dp),
                horizontalArrangement = Arrangement.End
            ) {
                if (negativeAction != null) {
                    Button(
                        modifier = Modifier.padding(end = 8.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.onError),
                        onClick = negativeAction.onNegativeAction
                    ) {
                        Text(text = stringResource(id = negativeAction.negativeBtnTxt))
                    }
                }
                if(positiveAction != null){
                    Button(
                        modifier = Modifier.padding(end = 8.dp),
                        onClick = positiveAction.onPositiveAction,
                    ) {
                        Text(text = stringResource(id = positiveAction.positiveBtnTxt))
                    }
                }
            }
        }
    )
}

@Composable
fun ProcessDialogQueue(
    dialogQueue: Queue<GenericDialogInfo>?,
) {
    dialogQueue?.peek()?.let { dialogInfo ->
        GenericDialog(
            onDismiss = dialogInfo.onDismiss,
            title = dialogInfo.title,
            description = dialogInfo.description,
            positiveAction = dialogInfo.positiveAction,
            negativeAction = dialogInfo.negativeAction
        )
    }
}