package org.ult1mma.userservice.dto

data class CreateUserRequest (
    val email : String,
    val password : String,
    val name : String
)