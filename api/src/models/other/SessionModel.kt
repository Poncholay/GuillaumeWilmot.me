package me.guillaumewilmot.api.models.other

import me.guillaumewilmot.api.models.UserModel

data class SessionModel(val validity: Long, val user: UserModel)