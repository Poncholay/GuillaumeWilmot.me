package me.guillaumewilmot.api

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

inline fun <reified T> genericType(): Type = object : TypeToken<T>() {}.type

inline fun <reified T> String.to(): T? = try {
    Gson().fromJson(this, genericType<T>())
} catch (e: Exception) {
    e.printStackTrace()
    null
}