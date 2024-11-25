package com.knu.navigation

import android.location.Location

sealed class NavigationActions {
    data class ToList(val location: Location) : NavigationActions()
    data class ToDetail(val uniqueKey: String) : NavigationActions()
}

