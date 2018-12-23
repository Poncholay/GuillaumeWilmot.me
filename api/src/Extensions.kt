package me.guillaumewilmot.api

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

inline fun <reified T> genericType(): Type = object : TypeToken<T>() {}.type

inline fun <reified T> String.to(): T? = try {
    GsonBuilder().setDateFormat("YYYY/MM/DD HH:mm").create().fromJson(this, genericType<T>())
} catch (e: Exception) {
    e.printStackTrace()
    null
}