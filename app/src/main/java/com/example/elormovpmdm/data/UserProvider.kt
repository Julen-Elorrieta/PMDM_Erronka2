package com.example.elormovpmdm.data

import com.example.elormovpmdm.domain.User
import javax.inject.Inject

class UserProvider @Inject constructor(){
    fun getUsers(): List<User> {
        return listOf(
            User.DrRobertoGarcia,
            User.DrRobertoGarcia2
        )
    }
}