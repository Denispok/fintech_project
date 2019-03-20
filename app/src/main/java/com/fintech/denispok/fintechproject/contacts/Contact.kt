package com.fintech.denispok.fintechproject.contacts

import java.io.Serializable
import kotlin.random.Random

data class Contact(val id: Int, val name: String) : Serializable {
    var points: Int = Random.nextInt(200)
    var color: Int = Random.nextInt(16777216) - 16777216

    fun parseInitials(): String {
        val names = name.split(" ")
        var initials = ""
        for (name in names) {
            initials += name[0].toUpperCase()
            if (initials.length >= 2) break
        }
        return initials
    }

    fun getPointsString(): String {
        val pointsText = when {
            points % 100 in 10..20 -> "баллов"
            points % 10 == 1 -> "балл"
            points % 10 in 2..4 -> "балла"
            else -> "баллов"
        }
        return "$points $pointsText"
    }
}
