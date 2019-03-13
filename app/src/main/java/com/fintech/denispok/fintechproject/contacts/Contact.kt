package com.fintech.denispok.fintechproject.contacts

import java.io.Serializable

data class Contact(val id: Int, val name: String) : Serializable {
    fun parseInitials(): String {
        val names = name.split(" ")
        var initials = ""
        for (name in names) {
            initials += name[0].toUpperCase()
            if (initials.length >= 2) break
        }
        return initials
    }
}
