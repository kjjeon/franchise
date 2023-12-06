package com.jjanjjan.franchise.shared.presenter.welcome

data class WelcomeModel(
    val messageList: List<String>,
    val messageVisibility: Boolean,
) {
    companion object {
        val placeholder = WelcomeModel(
            messageList = emptyList(),
            messageVisibility = false,
        )
    }
}