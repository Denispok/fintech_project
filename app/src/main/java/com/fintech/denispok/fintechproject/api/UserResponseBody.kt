package com.fintech.denispok.fintechproject.api

import com.fintech.denispok.fintechproject.api.entity.User

data class UserResponseBody(val user: User? = null, val message: String?, val status: String)
