package com.jayesh

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform