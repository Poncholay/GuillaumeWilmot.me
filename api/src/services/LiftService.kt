package me.guillaumewilmot.api.services

import me.guillaumewilmot.api.models.LiftModel

class LiftService {
    fun all(): List<LiftModel> {
        return ArrayList()
    }

    fun one(id: Long): LiftModel {
        return LiftModel()
    }
}