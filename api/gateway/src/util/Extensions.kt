package me.guillaumewilmot.api.gateway.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.google.gson.reflect.TypeToken

/**
 * Returns the type from a templated call
 */
inline fun <reified T> genericType(): Class<in T> = object : TypeToken<T>() {}.rawType

/**
 * Serializes a String into an object
 */
inline fun <reified T> String.to(): T? = try {
    ObjectMapper().registerKotlinModule().readValue(this, genericType<T>()) as T
} catch (e: Exception) {
    e.printStackTrace()
    null
}

/**
 * Serializes an object into a String
 */
fun Any.toJson(): String? = try {
    ObjectMapper().registerKotlinModule().writeValueAsString(this)
} catch (e: Exception) {
    e.printStackTrace()
    null
}