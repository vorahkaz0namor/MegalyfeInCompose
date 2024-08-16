package com.example.megalifecompose.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.ccink.common.extations.shadow
import com.ccink.resources.FourDp
import com.ccink.resources.GoBack
import com.ccink.resources.R
import com.ccink.resources.SixteenDp
import com.ccink.resources.SeventyTwoDp
import com.ccink.resources.TenDp
import com.ccink.resources.ThirtySixDp
import com.ccink.resources.colors

@Preview
@Composable
fun PreviewUserAgreement() {
    UserAgreementScreen {}
}

@Composable
fun UserAgreementScreen(
    navigateUp: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.systemBars)
            .background(Color.White)
            .padding(horizontal = TenDp)
            .padding(bottom = TenDp)
    ) {
        /**
         * User agreement
         */
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(state = rememberScrollState())
                .padding(top = SeventyTwoDp)
                .constrainAs(createRef()) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
                painter =
                    painterResource(
                        id = R.drawable.user_agreement_megalife
                    ),
                contentDescription = null
            )
        }

        /**
         * Back button
         */
        Image(
            modifier = Modifier
                .padding(top = TenDp)
                .shadow(
                    color = MaterialTheme.colors.colorD4AAFF,
                    blurRadius = FourDp,
                    borderRadius = ThirtySixDp
                )
                .constrainAs(createRef()) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .clickable { navigateUp() },
            painter = painterResource(R.drawable.ic_store_back),
            contentDescription = GoBack
        )

    }
}