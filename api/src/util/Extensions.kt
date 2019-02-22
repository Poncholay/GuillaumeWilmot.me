package me.guillaumewilmot.api.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.google.gson.reflect.TypeToken

inline fun <reified T> genericType(): Class<in T> = object : TypeToken<T>() {}.rawType

inline fun <reified T> String.to(): T? = try {
    ObjectMapper().registerKotlinModule().readValue(this, genericType<T>()) as T
} catch (e: Exception) {
    e.printStackTrace()
    null
}

fun Any.toJson(): String? = try {
    ObjectMapper().registerKotlinModule().writeValueAsString(this)
} catch (e: Exception) {
    e.printStackTrace()
    null
}