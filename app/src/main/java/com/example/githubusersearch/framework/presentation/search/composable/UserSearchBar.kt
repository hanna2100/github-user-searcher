package com.example.githubusersearch.framework.presentation.search.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.githubusersearch.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserSearchBar(
    onUserSearch: (searchText: String) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }
    val interactionSource = remember { MutableInteractionSource() }
    var searchText by remember { mutableStateOf("") }
    var isOpenSearchField by remember { mutableStateOf(false) }

    Column (modifier = Modifier
        .background(MaterialTheme.colors.background)
        .fillMaxWidth()
        .wrapContentHeight()
        .background(
            brush = Brush.linearGradient(
                colors = listOf(
                    MaterialTheme.colors.primary,
                    MaterialTheme.colors.primary,
                    MaterialTheme.colors.primaryVariant
                ),
                end = Offset(0f, Float.POSITIVE_INFINITY),
                start = Offset(Float.POSITIVE_INFINITY, 0f)
            ),
        )
        .padding(30.dp)
        .animateContentSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            AnimatedVisibility(visible = !isOpenSearchField) {
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            scope.launch {
                                isOpenSearchField = !isOpenSearchField
                                searchText = ""
                                delay(400) // 텍스트 필드가 열리는 애니메이션 시간 기다림.
                                focusRequester.requestFocus()
                            }
                        }
                    ,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_github_icon),
                        contentDescription = "",
                        colorFilter = tint(MaterialTheme.colors.onPrimary),
                        modifier = Modifier.size(50.dp)
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Column {
                        Text(
                            text = "Search",
                            style = MaterialTheme.typography.h5.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Medium
                            ),
                        )
                        Text(
                            text = " Users",
                            style = MaterialTheme.typography.subtitle1.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Medium
                            ),
                        )
                    }
                }
            }
            Row (
                modifier = Modifier
                    .height(46.dp)
                    .fillMaxWidth()
                    .offset(y = 10.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .border(
                            border = BorderStroke(1.dp, Color.White),
                            shape = RoundedCornerShape(40.dp)
                        )
                ) {
                    AnimatedVisibility(isOpenSearchField) {
                        val configuration = LocalConfiguration.current
                        val textFieldWidth = configuration.screenWidthDp.dp - 150.dp
                        val keyboardController = LocalSoftwareKeyboardController.current
                        BasicTextField(
                            value = searchText,
                            onValueChange = {
                                searchText = it
                            },
                            modifier = Modifier
                                .focusRequester(focusRequester)
                                .padding(20.dp, 10.dp, 20.dp, 4.dp)
                                .fillMaxHeight()
                                .width(textFieldWidth)
                            ,
                            textStyle = MaterialTheme.typography.h6.copy(
                                color = Color.White,
                                lineHeight = 24.sp,
                                textAlign = TextAlign.Start
                            ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            cursorBrush = SolidColor(Color.White),
                            singleLine = true,
                            maxLines = 1,
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    if (isOpenSearchField && searchText.isNotEmpty()) {
                                        keyboardController?.hide()
                                        onUserSearch(searchText)
                                    }
                                    isOpenSearchField = !isOpenSearchField
                                }
                            ),
                        )
                    }
                    IconButton(
                        onClick = {
                            scope.launch {
                                if (isOpenSearchField && searchText.isNotEmpty()) {
                                    onUserSearch(searchText)
                                    isOpenSearchField = !isOpenSearchField
                                } else { // 검색 텍스트 필드를 여는 경우. 키보드 자동 노출.
                                    isOpenSearchField = !isOpenSearchField
                                    searchText = ""
                                    delay(400) // 텍스트 필드가 열리는 애니메이션 시간 기다림.
                                    focusRequester.requestFocus()
                                }
                            }
                        }
                    ) {
                        Icon (
                            modifier = Modifier
                                .size(30.dp),
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}
