package com.example.monni.ui

sealed interface GeneralUiState{
    data class Error(val msg: String): GeneralUiState
    object Success: GeneralUiState
    object Default: GeneralUiState

}