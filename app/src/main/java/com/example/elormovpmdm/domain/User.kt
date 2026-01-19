package com.example.elormovpmdm.domain

import com.example.elormovpmdm.R

sealed class User (val name: Int, val email: Int) {
    data object DrRobertoGarcia: User(R.string.name_example, R.string.email_hint)
    data object DrRobertoGarcia2: User(R.string.name_example, R.string.email_hint)
}