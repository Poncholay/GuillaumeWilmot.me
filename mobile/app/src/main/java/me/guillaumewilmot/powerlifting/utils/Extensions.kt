package me.guillaumewilmot.powerlifting.utils

import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v4.content.res.ResourcesCompat
import android.view.View
import me.guillaumewilmot.powerlifting.R

fun errorSnackbar(root: View, msg: String) = Snackbar.make(
    root,
    msg,
    Snackbar.LENGTH_INDEFINITE
).setAction(root.context.getString(R.string.dismiss_text)) {}.show()

fun Snackbar.setBackgroundColor(color: Int): Snackbar = apply { view.setBackgroundColor(color) }

fun Context.successSnackbar(root: View, msg: String? = null) = Snackbar.make(root, msg
    ?: getString(R.string.success_text), Snackbar.LENGTH_SHORT)
    .setAction(getString(R.string.dismiss_text)) {}
    .setBackgroundColor(ResourcesCompat.getColor(resources, R.color.success, null))
    .setActionTextColor(ResourcesCompat.getColor(resources, R.color.text_primary, null))
    .show()
