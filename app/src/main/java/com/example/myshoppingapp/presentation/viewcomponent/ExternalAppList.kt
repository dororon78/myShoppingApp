package com.example.myshoppingapp.presentation.viewcomponent

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myshoppingapp.presentation.viewmodels.ExternalAppListViewModel

@Composable
fun ExternalAppList(
    viewModel: ExternalAppListViewModel = hiltViewModel()
) {
    val list by viewModel.apps.observeAsState(listOf())
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(10.dp)
            .testTag("external_app_launcher")
    ) {
        Text(
            "앱 실행",
            modifier = Modifier
                .padding(bottom = 10.dp),
            style = TextStyle(
                fontSize = 15.sp
            )
        )

        LazyRow(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            items(list) { info ->
                if (info.executable || info.installable) {
                    IconButton(
                        modifier = Modifier
                            .padding(10.dp)
                            .height(80.dp)
                            .width(80.dp),
                        onClick = {
                            viewModel.getIntentForExecution(
                                info,
                                startActivity = { context.startActivity(it) })
                        }
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            if (info.executable) {
                                Image(
                                    context.packageManager.getApplicationIcon(info.packageName)
                                        .toBitmap()
                                        .asImageBitmap(),
                                    "",
                                    contentScale = ContentScale.FillHeight,
                                    modifier = Modifier
                                        .weight(1f, true)
                                )
                                Spacer(modifier = Modifier.height(5.dp))
                                Text(info.name)
                            } else {
                                Text(info.name)
                                Text("설치하기")
                            }
                        }
                    }
                }
            }
        }
    }
}
