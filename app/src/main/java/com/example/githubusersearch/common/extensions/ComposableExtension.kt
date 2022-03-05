package com.example.githubusersearch.common.extensions

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.*

@Composable
fun LazyListState.OnBottomReached(
    loadMore: ()->Unit
) {
    // 더 로드 해야 함을 알려주는 state object
    val shouldLoadMore = remember {
        // 다른 상태 객체에서 특정 상태가 계산되거나 파생되는 경우 derivedStateOf를 사용
        derivedStateOf {
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                ?: return@derivedStateOf true

            val isLastItemReached = lastVisibleItem.index == layoutInfo.totalItemsCount - 1
            isLastItemReached
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