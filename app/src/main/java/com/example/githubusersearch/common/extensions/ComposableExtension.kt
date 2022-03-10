package com.example.githubusersearch.common.extensions

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.Dp
import com.example.githubusersearch.common.constant.ITEM_SIZE_THAT_NOT_NOTIFY_EVEN_IF_LAST_ITEM_REACHED

@Composable
fun LazyListState.OnBottomReached(
    loadMore: ()->Unit
) {
    // 더 로드 해야 함을 알려주는 state object
    val shouldLoadMore = remember {
        // 다른 상태 객체에서 특정 상태가 계산되거나 파생되는 경우 derivedStateOf를 사용
        derivedStateOf {
            val items = layoutInfo.visibleItemsInfo
            val itemSize = layoutInfo.totalItemsCount

            if (items.isEmpty()) {
                return@derivedStateOf false
            }
            val lastVisibleItem = items.lastOrNull()
                ?: return@derivedStateOf true

            val isLastItemReached = lastVisibleItem.index == itemSize - 1
            if (itemSize < ITEM_SIZE_THAT_NOT_NOTIFY_EVEN_IF_LAST_ITEM_REACHED) {
                false
            } else {
                isLastItemReached
            }
        }
    }

    LaunchedEffect(shouldLoadMore) {
        // Compose의 상태(State<T>)를 Flow로 변환. 수집된 상태가 이전에 내보낸 값과 같지 않을 경우 새 값을 Flow로 내보냄.
        snapshotFlow { shouldLoadMore.value }
            .collect { isLastItemReached ->
                if (isLastItemReached) {
                    loadMore()
                }
            }
    }
}

@Suppress("UnnecessaryComposedModifier")
fun Modifier.topRectBorder(width: Dp = Dp.Hairline, brush: Brush = SolidColor(Color.Black)): Modifier = composed(
    factory = {
        this.then(
            Modifier.drawWithCache {
                onDrawWithContent {
                    drawContent()
                    drawLine(brush, Offset(width.value, 0f), Offset(size.width - width.value, 0f))
                }
            }
        )
    },
    inspectorInfo = debugInspectorInfo {
        name = "border"
        properties["width"] = width
        if (brush is SolidColor) {
            properties["color"] = brush.value
            value = brush.value
        } else {
            properties["brush"] = brush
        }
        properties["shape"] = RectangleShape
    }
)