package com.d.beteasier.api

import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.d.beteasier.R
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Match(
    val id: String,
    val firstTeam: String,
    val secondTeam: String,
    val dateTime: String,
    val category: Category,
    val firstRate: Double,
    val secondRate: Double,
    val isFinished: Boolean,
    val firstTeamScore: Int? = null,
    val secondTeamScore: Int? = null
) : Parcelable

enum class Category(@DrawableRes val imageRes: Int) {
    Football(R.drawable.ic_football),
    Basketball(R.drawable.ic_basketball),
    Baseball(R.drawable.ic_baseball);
}
