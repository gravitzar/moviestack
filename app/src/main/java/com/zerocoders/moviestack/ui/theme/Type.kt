package com.zerocoders.moviestack.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.zerocoders.moviestack.R

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)


val Montserrat = FontFamily(
    Font(R.font.montserrat, FontWeight.Normal),
    Font(R.font.montserrat_medium, FontWeight.Medium),
    Font(R.font.montserrat_black, FontWeight.Black),
)

val Roboto = FontFamily(
    Font(R.font.roboto, FontWeight.Normal),
    Font(R.font.roboto_medium, FontWeight.Medium),
    Font(R.font.roboto_bold, FontWeight.Bold),
    Font(R.font.roboto_black, FontWeight.Black)
)

val robotoNormalTextStyle = TextStyle(
    fontFamily = Roboto,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp,
    letterSpacing = 0.25.sp,
)

val robotoMediumTextStyle = TextStyle(
    fontFamily = Roboto,
    fontWeight = FontWeight.Medium,
    fontSize = 12.sp,
    letterSpacing = 0.25.sp
)

val robotoBoldTextStyle = TextStyle(
    fontFamily = Roboto,
    fontWeight = FontWeight.Bold,
    fontSize = 12.sp,
    letterSpacing = 0.25.sp,
)

val robotoBlackTextStyle = TextStyle(
    fontFamily = Roboto,
    fontWeight = FontWeight.Black,
    fontSize = 12.sp,
    letterSpacing = 0.25.sp,
)

val montserratNormalTextStyle = TextStyle(
    fontFamily = Montserrat,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp,
    letterSpacing = 0.25.sp
)

val montserratMediumTextStyle = TextStyle(
    fontFamily = Montserrat,
    fontWeight = FontWeight.Medium,
    fontSize = 12.sp,
    letterSpacing = 0.25.sp
)

val montserratBlackTextStyle = TextStyle(
    fontFamily = Montserrat,
    fontWeight = FontWeight.Black,
    fontSize = 12.sp,
    letterSpacing = 0.25.sp
)