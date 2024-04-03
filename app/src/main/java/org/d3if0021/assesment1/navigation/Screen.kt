package org.d3if0021.assesment1.navigation




sealed class Screen(val route: String) {
    data object  Home: Screen("mainScreen")
    data object  About: Screen("AboutScreen")
}
