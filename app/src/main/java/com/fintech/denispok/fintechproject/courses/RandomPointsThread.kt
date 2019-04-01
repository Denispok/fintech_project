package com.fintech.denispok.fintechproject.courses

import android.os.Handler
import com.fintech.denispok.fintechproject.courses.CoursesFragment.Companion.RANDOM_POINTS_CODE
import kotlin.random.Random

class RandomPointsThread(private val handler: Handler, private val generatedPointsCount: Int) : Thread() {

    override fun run() {
        Thread.sleep(2000)
        val result = arrayOfNulls<Int>(generatedPointsCount)
        for (i in 0 until result.size) {
            result[i] = Random.nextInt(200)
        }
        handler.obtainMessage(RANDOM_POINTS_CODE, result.requireNoNulls()).sendToTarget()
    }
}