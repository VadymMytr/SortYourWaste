package vadym.my.wastesorting.presentation.theme

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier

val ModifierFullWidth = Modifier
    .fillMaxWidth()
    .padding(horizontal = padding2X)

val ModifierFullWidthMarginTop1X = Modifier
    .padding(top = padding1X)
    .fillMaxWidth()
    .padding(horizontal = padding2X)

val ModifierFullWidthMarginTop2X = Modifier
    .padding(top = padding2X)
    .fillMaxWidth()
    .padding(horizontal = padding2X)

val ButtonModifierMarginTop1X = ModifierFullWidthMarginTop1X.height(buttonHeightDefault)

val ButtonModifierMarginTop2X = ModifierFullWidthMarginTop2X.height(buttonHeightDefault)
