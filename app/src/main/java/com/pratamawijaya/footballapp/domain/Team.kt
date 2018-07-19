package com.pratamawijaya.footballapp.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Team(
        val name: String,
        val badge: String,
        val desc: String
) : Parcelable