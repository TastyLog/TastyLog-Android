package com.knu.common.fragment

import android.view.View
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.toast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Fragment.snackBar(
    anchorView: View,
    message: () -> String,
) {
    Snackbar.make(anchorView, message(), Snackbar.LENGTH_SHORT).show()
}

fun Fragment.stringOf(
    @StringRes resId: Int,
) = getString(resId)

fun Fragment.colorOf(
    @ColorRes resId: Int,
) = ContextCompat.getColor(requireContext(), resId)

fun Fragment.drawableOf(
    @DrawableRes resId: Int,
) = ContextCompat.getDrawable(requireContext(), resId)
