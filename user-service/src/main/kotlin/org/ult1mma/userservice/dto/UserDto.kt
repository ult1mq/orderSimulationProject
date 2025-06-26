package org.ult1mma.userservice.dto

data class UserDto (
    val id : Long,
    val email : String,
    val password : String,
    val name : String
)