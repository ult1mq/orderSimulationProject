package org.ult1mma.userservice.dto

import org.ult1mma.userservice.model.User

fun User.toDto(): UserDto = UserDto(
    id = this.id,
    email = this.email,
    password = this.password,
    name = this.name,
)

fun CreateUserRequest.toEntity(): User = User(
    email = this.email,
    password = this.password,
    name = this.name
)