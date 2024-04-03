package org.d3if0021.assesment1.model

import androidx.annotation.DrawableRes

data class Buku(
    val name: String,
    @DrawableRes val imageResId : Int,
    val harga: Float
)
