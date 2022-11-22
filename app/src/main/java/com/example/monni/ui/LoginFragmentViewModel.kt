package com.example.monni.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monni.data.Resource
import com.example.monni.data.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginFragmentViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel(){

    private val _uiState: MutableStateFlow<LoginFragmentUiState> =
        MutableStateFlow(LoginFragmentUiState.Default)
    val uiState: StateFlow<LoginFragmentUiState> = _uiState



    fun login(email: String, password: String){
        viewModelScope.launch {
            _uiState.value = LoginFragmentUiState.Default
            val userId = repository.signInWithEmailAndPassword(email, password)
            println(userId)
            when (userId) {
                is Resource.Success -> {
                    _uiState.value = LoginFragmentUiState.Success(userId.data)
                }

                is Resource.Error -> {
                    _uiState.value = LoginFragmentUiState.Error("ola")
                }
                null -> {
                    _uiState.value = LoginFragmentUiState.Error("User not found")
                }
                else -> {}
            }
        }
    }
}