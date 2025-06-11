package com.rul.compose_example.new_jetpack_compose.core_components_3

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun UserInteractionExample() {

    val customSelectionColor = TextSelectionColors(
        handleColor = Color.Red,
        backgroundColor = Color.Red.copy(alpha = 0.4f)
    )
    val androidComposeLink = "https://developer.android.com/jetpack/compose"
    val composeUserInteractionLink =
        "https://developer.android.com/develop/ui/compose/text/user-interactions"
    val uriHandler = LocalUriHandler.current

    val link = LinkAnnotation.Url(
        androidComposeLink,
        TextLinkStyles(style = SpanStyle(color = Color.Blue))
    ) {
        val url = (it as LinkAnnotation.Url).url
        uriHandler.openUri(url)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SelectionContainer {
            CompositionLocalProvider(LocalTextSelectionColors provides customSelectionColor) {
                Column {
                    BasicText("This is a selected text One")
                    BasicText("This is a selected text Two")
                    BasicText("This is a selected text Three")
                    DisableSelection {
                        BasicText("This is a not-selected text one")
                        BasicText("This is a not-selected text two")
                        BasicText("This is a not-selected text three")
                    }
                    Text("This is a selected text Four")
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        //Annotated String With Link
        Text(buildAnnotatedString {
            append("Go to the ")
            withLink(
                LinkAnnotation.Url(
                    androidComposeLink,
                    TextLinkStyles(
                        style = SpanStyle(
                            color = Color.Blue,
                            fontStyle = FontStyle.Italic
                        )
                    )
                )
            ) {
                append("Android Developer (Compose guidance)")
            }
            append(" website, and check out the ")
            withLink(
                LinkAnnotation.Url(
                    composeUserInteractionLink,
                    TextLinkStyles(
                        style = SpanStyle(color = Color.Red, fontWeight = FontWeight.Bold)
                    )
                )
            ) {
                append("User interaction")
            }
            append(".")
        }, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(16.dp))
        //Annotated String With Listener
        //The link using openUri method of the LocalUriHandler
        Text(buildAnnotatedString {
            append("Build better apps faster with ")
            /*val link = LinkAnnotation.Url(
                androidComposeLink,
                TextLinkStyles(style = SpanStyle(color = Color.Blue))
            ) {
                val url = (it as LinkAnnotation.Url).url
                uriHandler.openUri(url)
            }*/
            withLink(link) { append("Jetpack Compose") }
        }, textAlign = TextAlign.Center)

    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewUserInteractionExample() {
    UserInteractionExample()
}
