package com.example.megalifecompose.ui.main.nav_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ccink.common.extations.navigateWithPopUpTo
import com.ccink.common.extations.shadow
import com.ccink.resources.BtnEnabled
import com.ccink.resources.BtnNotEnabled
import com.ccink.resources.NoRippleTheme
import com.ccink.resources.R
import com.ccink.resources.colors
import com.ccink.resources.styles
import com.example.megalifecompose.navigation.BottomNavGraphScreen
import com.example.megalifecompose.navigation.MainBottomNavHost

@Preview
@Composable
fun PreviewBottom() {
    BottomNavigationBar()
}

@Composable
fun BottomNavigationBar() {
    var navigationSelectedItem by remember {
        mutableIntStateOf(0)
    }
    val profileNavHostController = rememberNavController()

    Scaffold(modifier = Modifier
        .fillMaxSize()
        .windowInsetsPadding(WindowInsets.navigationBars),
        containerColor = Color.White,
        floatingActionButton = {

            InitFloatingActionButton(
                navHostController = profileNavHostController,
                rememberItemIndex = {
                    navigationSelectedItem = it
                }
            )

        },
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = {

            InitBottomBar(
                navHostController = profileNavHostController,
                rememberItemIndex = {
                    it?.let { navigationSelectedItem = it }
                    navigationSelectedItem
                }
            )

        }

    ) { paddingValues ->

        MainBottomNavHost(navController = profileNavHostController, paddingValues)

    }
}

@Composable
private fun InitBottomBar(navHostController: NavHostController, rememberItemIndex: (Int?) -> Int) {

    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
        NavigationBar(
            modifier = Modifier
                .shadow(
                    color = Color.Black,
                    borderRadius = 0.dp,
                    blurRadius = 80.dp,
                    offsetX = 0.dp,
                    offsetY = 50.dp,
                    spread = 1.dp
                )
                .fillMaxWidth()
                .clip(RoundedCornerShape(28.dp, 28.dp, 0.dp, 0.dp)),
            containerColor = Color.White,
        ) {
            InitNavigationBarItem(navHostController = navHostController, rememberItemIndex = rememberItemIndex)
        }
    }
}

@Composable
private fun RowScope.InitNavigationBarItem(navHostController: NavHostController, rememberItemIndex: (Int?) -> Int) {

    BottomNavigationItem().bottomNavigationItems().forEachIndexed { index, navigationItem ->
        NavigationBarItem(selected = false, label = {
            Text(
                navigationItem.label,
                color = if (index == rememberItemIndex(null)) MaterialTheme.colors.colorB066FF else MaterialTheme.colors.color9DB2CE,
                style = MaterialTheme.styles.s12w400
            )
        }, icon = {
            Icon(
                painterResource(id = if (index == rememberItemIndex(null)) navigationItem.iconSelected else navigationItem.iconUnselected),
                contentDescription = navigationItem.label,
                tint = if (index == rememberItemIndex(null)) MaterialTheme.colors.colorB066FF else MaterialTheme.colors.color9DB2CE
            )
        }, onClick = {
            rememberItemIndex(index)
            navHostController.navigateWithPopUpTo(navigationItem.route)
        })
    }

}

@Composable
private fun InitFloatingActionButton(navHostController: NavHostController, rememberItemIndex: (Int) -> Unit) {
    Column(modifier = Modifier.offset(y = 90.dp)) {

        Box(
            modifier = Modifier
                .background(color = Color.White, shape = CircleShape)
                .border(
                    width = 1.dp, color = Color.White, shape = CircleShape
                )
                .padding(top = 7.dp, bottom = 5.dp, end = 7.dp, start = 7.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(color = BtnNotEnabled, shape = CircleShape)
                    .border(
                        width = 1.dp, color = BtnNotEnabled, shape = CircleShape
                    )
                    .padding(top = 5.dp, bottom = 5.dp, end = 5.dp, start = 5.dp)


            ) {
                FloatingActionButton(
                    modifier = Modifier.size(75.dp), onClick = {
                        rememberItemIndex(
                            BottomNavigationItem().bottomNavigationItems().size + 1
                        )
                        navHostController.navigateWithPopUpTo(route = BottomNavGraphScreen.Store.route)
                    }, shape = CircleShape, elevation = FloatingActionButtonDefaults.elevation(0.dp), containerColor = BtnEnabled

                ) {
                    Icon(painter = painterResource(id = R.drawable.ic_shop), contentDescription = null, tint = Color.White)
                }
            }
        }


    }
}

