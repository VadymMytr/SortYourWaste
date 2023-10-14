package vadym.my.wastesorting.presentation.theme

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = getColorPalette(),
        content = content,
    )
}

@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    @StringRes stringRes: Int,
    modifier: Modifier = ButtonModifierMarginTop2X,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(disabledContainerColor = osloGray, disabledContentColor = darkGray),
        modifier = modifier,
    ) {
        LargeBoldText(stringRes = stringRes, color = berylGreen, modifier = ModifierFullWidth)
    }
}

@Composable
fun PrimaryTextButton(
    onClick: () -> Unit,
    @StringRes stringRes: Int,
    modifier: Modifier = ButtonModifierMarginTop1X,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
    ) {
        LargeBoldText(stringRes = stringRes, modifier = ModifierFullWidth)
    }
}

@Composable
fun PrimaryText(
    @StringRes stringRes: Int,
    color: Color = getTextColor(),
    modifier: Modifier = ModifierFullWidth.padding(top = padding1X),
) {
    PrimaryText(text = stringResource(id = stringRes), color = color, modifier = modifier)
}

@Composable
fun LargeBoldText(
    @StringRes stringRes: Int,
    color: Color = getTextColor(),
    modifier: Modifier = ModifierFullWidth.padding(top = padding2X),
) {
    LargeBoldText(text = stringResource(id = stringRes), color = color, modifier = modifier)
}

@Composable
fun TitleText(
    @StringRes stringRes: Int,
    color: Color = getTextColor(),
    modifier: Modifier = ModifierFullWidth.padding(top = padding3X),
) {
    TitleText(text = stringResource(id = stringRes), color = color, modifier = modifier)
}

@Composable
fun PrimaryText(
    text: String,
    color: Color = getTextColor(),
    modifier: Modifier = ModifierFullWidth.padding(top = padding1X),
) {
    Text(
        text = text,
        color = color,
        fontFamily = fontRegular,
        fontSize = textSizeRegular,
        textAlign = TextAlign.Justify,
        modifier = modifier,
    )
}

@Composable
fun LargeBoldText(
    text: String,
    color: Color = getTextColor(),
    modifier: Modifier = ModifierFullWidth.padding(top = padding2X),
) {
    Text(
        text = text,
        color = color,
        fontFamily = fontBold,
        fontSize = textSizeLarge,
        textAlign = TextAlign.Center,
        modifier = modifier,
    )
}

@Composable
fun TitleText(
    text: String,
    color: Color = getTextColor(),
    modifier: Modifier = ModifierFullWidth.padding(top = padding3X),
) {
    Text(
        text = text,
        color = color,
        fontFamily = fontBlack,
        fontSize = textSizeTitle,
        textAlign = TextAlign.Center,
        modifier = modifier,
    )
}
