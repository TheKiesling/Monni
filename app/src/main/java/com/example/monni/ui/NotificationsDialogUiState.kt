package com.example.monni.ui

sealed interface NotificationsDialogUiState {

    data class Error(val msg: String, val type: Int): NotificationsDialogUiState
    object Success: NotificationsDialogUiState
    object Default: NotificationsDialogUiState

}