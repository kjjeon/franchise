package com.jjanjjan.franchise.shared

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform