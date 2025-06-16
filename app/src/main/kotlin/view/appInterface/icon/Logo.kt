package view.appInterface.icon

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.coremapx.app.theme.AppTheme

@OptIn(ExperimentalComposeUiApi::class)
@Suppress("ktlint:standard:function-naming")
@Composable
fun Logo(
    size: Dp,
    backgroundColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.FillBounds,
) {
    Box(modifier = modifier) {
        Image(
            painter = painterResource("drawable/logo_background.svg"),
            contentDescription = "Logo Background",
            modifier =
                Modifier
                    .size(size)
                    .aspectRatio(1f),
            contentScale = contentScale,
            colorFilter = ColorFilter.tint(backgroundColor),
        )

        Image(
            painter = painterResource("drawable/logo_icon.svg"),
            contentDescription = "Logo Icon",
            modifier =
                Modifier
                    .size(size * 0.7f)
                    .aspectRatio(2f)
                    .align(Alignment.Center),
            contentScale = contentScale,
            colorFilter = ColorFilter.tint(contentColor),
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewLogo1() {
    AppTheme {
        Logo(
            size = 500.dp,
            backgroundColor = MaterialTheme.colors.onSurface,
            contentColor = MaterialTheme.colors.background,
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewLogo2() {
    AppTheme {
        Logo(
            size = 500.dp,
            backgroundColor = MaterialTheme.colors.background,
            contentColor = MaterialTheme.colors.onSurface,
        )
    }
}
