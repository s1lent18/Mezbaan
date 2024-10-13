package com.example.mezbaan.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

data class Dimens(
    val extraSmall: Dp = 0.dp,
    val small1: Dp = 0.dp,
    val small2: Dp = 0.dp,
    val small3: Dp = 0.dp,
    val medium1: Dp = 0.dp,
    val medium2: Dp = 0.dp,
    val medium3: Dp = 0.dp,
    val large: Dp = 0.dp,
    val buttonHeight: Dp = 40.dp,
    val buttonWidth: Dp = 0.dp,
    val logoSize: Dp = 42.dp,
    val heading: TextUnit = 0.sp,
    val fontsize: TextUnit = 0.sp,
    val buttontext: TextUnit = 0.sp,
    val cardfont: TextUnit = 0.sp,
    val scroll: Dp = 0.dp,
    val cards: Dp = 0.dp,
    val scrollspacer: Dp = 0.dp,
    val scrollwidth: Dp = 0.dp,
    val iconsize: Dp = 0.dp,
    val cardsize: Dp = 0.dp
)

val CompactDimens = Dimens(
    small1 = 10.dp,
    small2 = 15.dp,
    small3 = 20.dp,
    medium1 = 30.dp,
    medium2 = 36.dp,
    medium3 = 30.dp,
    large = 80.dp,
    buttonHeight = 60.dp,
    buttonWidth = 80.dp,
    logoSize = 50.dp,
    iconsize = 20.dp,
    heading = 30.sp,
    fontsize = 17.sp,
    buttontext = 15.sp,
    cardfont = 12.sp,
    cards = 240.dp,
    scrollspacer = 15.dp,
    scroll = 100.dp,
    scrollwidth = 100.dp,
    cardsize = 130.dp
)

val MediumDimens = Dimens(
    small1 = 10.dp,
    small2 = 15.dp,
    small3 = 20.dp,
    medium1 = 30.dp,
    medium2 = 36.dp,
    medium3 = 40.dp,
    large = 110.dp,
    logoSize = 55.dp
)

val ExpandedDimens = Dimens(
    small1 = 15.dp,
    small2 = 20.dp,
    small3 = 25.dp,
    medium1 = 35.dp,
    medium2 = 30.dp,
    medium3 = 45.dp,
    large = 130.dp,
    logoSize = 72.dp
)
