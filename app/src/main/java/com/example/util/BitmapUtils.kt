package com.example.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.LruCache
import androidx.core.graphics.scale
import java.io.ByteArrayOutputStream

object BitmapUtils {

    // Memory cache for decoded Base64 Bitmaps to ensure 60fps buttery smooth UI
    private val memoryCache: LruCache<String, Bitmap> = object : LruCache<String, Bitmap>(20) {
        override fun sizeOf(key: String, value: Bitmap): Int {
            return value.byteCount / 1024
        }
    }

    /**
     * Decodes and samples a bitmap from a content Uri to fit within specified dimensions.
     * Prevents high memory consumption and ashmem allocation issues on modern Android versions.
     */
    fun decodeSampledBitmapFromUri(
        context: Context,
        uri: Uri,
        reqWidth: Int = 400,
        reqHeight: Int = 400
    ): Bitmap? {
        return try {
            // First decode with inJustDecodeBounds=true to check dimensions
            val options = BitmapFactory.Options().apply {
                inJustDecodeBounds = true
            }
            context.contentResolver.openInputStream(uri)?.use { stream ->
                BitmapFactory.decodeStream(stream, null, options)
            }

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)

            // Decode bitmap with inSampleSize set, ARGB_8888, and inMutable = true
            options.inJustDecodeBounds = false
            options.inPreferredConfig = Bitmap.Config.ARGB_8888
            options.inMutable = true

            val decoded = context.contentResolver.openInputStream(uri)?.use { stream ->
                BitmapFactory.decodeStream(stream, null, options)
            } ?: return null

            // Scale to exact target bounds if still larger
            val result = if (decoded.width > reqWidth || decoded.height > reqHeight) {
                val scale = Math.min(
                    reqWidth.toFloat() / decoded.width,
                    reqHeight.toFloat() / decoded.height
                )
                val destWidth = (decoded.width * scale).toInt().coerceAtLeast(1)
                val destHeight = (decoded.height * scale).toInt().coerceAtLeast(1)
                decoded.scale(destWidth, destHeight)
            } else {
                decoded
            }
            result
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Encodes a bitmap to a compact Base64 JPEG string.
     */
    fun bitmapToBase64(bitmap: Bitmap, quality: Int = 80): String? {
        return try {
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            val bytes = outputStream.toByteArray()
            val base64Str = Base64.encodeToString(bytes, Base64.NO_WRAP)
            val fullStr = "data:image/jpeg;base64,$base64Str"
            memoryCache.put(fullStr.hashCode().toString(), bitmap)
            fullStr
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Decodes a Base64 string into a sampled Bitmap safely with high-speed memory caching.
     */
    fun decodeBase64ToBitmap(
        base64String: String,
        reqWidth: Int = 400,
        reqHeight: Int = 400
    ): Bitmap? {
        val cacheKey = "${base64String.hashCode()}_${reqWidth}x${reqHeight}"
        val cached = memoryCache.get(cacheKey)
        if (cached != null && !cached.isRecycled) {
            return cached
        }

        return try {
            val cleanData = if (base64String.contains(",")) {
                base64String.substringAfter(",")
            } else {
                base64String
            }
            val decodedBytes = Base64.decode(cleanData, Base64.DEFAULT)

            val options = BitmapFactory.Options().apply {
                inJustDecodeBounds = true
            }
            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size, options)

            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
            options.inJustDecodeBounds = false
            options.inPreferredConfig = Bitmap.Config.ARGB_8888
            options.inMutable = true

            val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size, options)
            if (bitmap != null) {
                memoryCache.put(cacheKey, bitmap)
            }
            bitmap
        } catch (e: Exception) {
            null
        }
    }

    private fun calculateInSampleSize(
        options: BitmapFactory.Options,
        reqWidth: Int,
        reqHeight: Int
    ): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val halfHeight = height / 2
            val halfWidth = width / 2
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize.coerceAtLeast(1)
    }
}
