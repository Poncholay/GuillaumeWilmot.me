package me.guillaumewilmot.api.gateway.models.other

import me.guillaumewilmot.api.gateway.models.UserModel

data class SessionModel(val validity: Long, val user: UserModel)