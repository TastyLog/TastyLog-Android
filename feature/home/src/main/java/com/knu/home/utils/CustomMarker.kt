package com.knu.home.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import androidx.core.graphics.drawable.toBitmap
import coil.imageLoader
import coil.request.ImageRequest

suspend fun createCustomMarker(context: Context, imageUrl: String, name: String): Bitmap {
    val bitmap = run {
        val request = ImageRequest.Builder(context)
            .data(imageUrl)
            .size(100)
            .allowHardware(false) // hardware 가속 사용 x
            .build()
        request.context.imageLoader.execute(request).drawable?.toBitmap()
    } ?: throw IllegalArgumentException("Failed to load image")

    val markerSize = 100
    val markerBitmap = Bitmap.createBitmap(markerSize, markerSize, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(markerBitmap)
    val paint = Paint()

    val rect = Rect(0, 0, markerSize, markerSize)
    val radius = markerSize / 2f

    paint.isAntiAlias = true // 안티앨리어싱

    canvas.drawCircle(radius, radius, radius, paint) // 원형으로 클리핑 처리

    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN) // 이미지 그리기
    canvas.drawBitmap(bitmap, null, rect, paint)

    return markerBitmap
}
