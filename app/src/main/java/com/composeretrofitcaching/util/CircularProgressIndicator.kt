package com.composeretrofitcaching.util

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composeretrofitcaching.R

@Composable
fun CustomCircularProgressIndicator(
    isDisplayed: Boolean
) {
    if (isDisplayed) {
        Box(
            modifier = Modifier.wrapContentSize(),
            contentAlignment = Alignment.TopCenter,

            ) {
            Column {
                androidx.compose.material3.CircularProgressIndicator(
                    modifier = Modifier.padding(16.dp),
                    color = colorResource(id = R.color.purple_200),
                    strokeWidth = Dp(value = 4F)
                )
            }

        }
    }
}