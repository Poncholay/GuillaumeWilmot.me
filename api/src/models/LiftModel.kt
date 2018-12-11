package me.guillaumewilmot.api.models

import java.util.*

class LiftModel {
    var id: Long = 0
    var exercise_id: Long = 0
    var weight: Float = 0.0f
    var reps: Int = 0
    var date: Date = Date()
    var image_url: String? = null
}