package com.example.monni.ui

interface LoginFragmentUiState {
    data class Error(val message: String): LoginFragmentUiState
    data class Success(val data: String?): LoginFragmentUiState
    object Default: LoginFragmentUiState
}