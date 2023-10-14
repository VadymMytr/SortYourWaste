package vadym.my.wastesorting.presentation.introduction

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import vadym.my.wastesorting.R

sealed class IntroductionScreenInfo(
    @DrawableRes
    val iconDrawable: Int,
    @StringRes
    val title: Int,
    @StringRes
    val description: Int,
) {
    data object Introduction : IntroductionScreenInfo(
        iconDrawable = R.drawable.ic_intro_trash_cans,
        title = R.string.intro_title,
        description = R.string.intro_description,
    )

    class Permission(
        @DrawableRes
        iconDrawable: Int,
        @StringRes
        title: Int,
        @StringRes
        description: Int,
        val code: String,
    ) : IntroductionScreenInfo(
        iconDrawable = iconDrawable,
        title = title,
        description = description,
    )
}
