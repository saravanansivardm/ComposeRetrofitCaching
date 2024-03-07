package com.composeretrofitcaching

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.composeretrofitcaching.util.NetworkManager
import com.composeretrofitcaching.util.Resource
import com.composeretrofitcaching.view.screens.UserListItem
import com.composeretrofitcaching.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CallApi(
                this@MainActivity
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CallApi(
    mainActivity: MainActivity,
    viewModel: UserViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
//    val scaffoldState = rememberScaffoldState()
    val getAllUserData = viewModel.getUserData.observeAsState()
    val getAllUserDataOffline = viewModel.getUserDataOffline.observeAsState()

    val internetConnectivityValue = remember() {
        mutableStateOf(false)
    }
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
//            scaffoldState = scaffoldState
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Green)
                        .padding(15.dp)
                ) {
                    Text(
                        text = "User Live Data",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                }
                /*LaunchedEffect(key1 = true) {
                    scope.launch {
                        val result = viewModel.getUserData()

                        if (result is Resource.Success) {
                            Toast.makeText(context, "Fetching data success!", Toast.LENGTH_SHORT)
                                .show()
                        } else if (result is Resource.Error) {
                            Toast.makeText(context, "Error: ${result.message}", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }*/

                val networkManager = NetworkManager(context)
                networkManager.observe(mainActivity) {
                    if (!it) {
                        internetConnectivityValue.value = it
//                        Log.e("internet_log_off", "true")
                    } else {
                        internetConnectivityValue.value = it
//                        Log.e("internet_log_on", "true")
                        scope.launch {
                            viewModel.getUserData()
                        }
                    }
                }
                if (!viewModel.isLoading.value || getAllUserData.value == null || getAllUserDataOffline.value == null && !internetConnectivityValue.value) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                    }
                } else {


                    Log.e("int_con_log", internetConnectivityValue.value.toString())
                    Log.e("isLoading_log", viewModel.isLoading.value.toString())
                    Log.e("Offline_01_log", getAllUserDataOffline.value?.size.toString())
                    Log.e("OnLine_log", getAllUserData.value?.size.toString())

                    if (viewModel.isLoading.value && viewModel.getUserData.value != null && internetConnectivityValue.value) {
                        Log.e("Offline_02_log", getAllUserDataOffline.value?.size.toString())
//                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT)
//                        .show()
                        LazyColumn(
                            modifier = Modifier.padding(10.dp)
                        ) {
                            items(getAllUserData.value!!.size) { index ->
                                UserListItem(getAllUserData.value!![index], Color.Red)
                            }
                        }
                    } else {
                        if (getAllUserDataOffline.value != null) {
//                        Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT)
//                            .show()
                            LazyColumn(
                                modifier = Modifier.padding(10.dp)
                            ) {
                                items(getAllUserDataOffline.value!!.size) { index ->
                                    UserListItem(
                                        getAllUserDataOffline.value!![index],
                                        Color.Magenta
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}