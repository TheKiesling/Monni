package com.example.monni.ui

import android.view.LayoutInflater
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.monni.R
import com.example.monni.data.local.source.CategoryDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LimitDialogViewModel @Inject constructor(): ViewModel(){

    private var limit: Double = 0.0
    private var amount: Double = 0.0

    private val _uiState: MutableStateFlow<GeneralUiState> =
        MutableStateFlow(GeneralUiState.Default)
    val uiState: StateFlow<GeneralUiState> = _uiState

    fun setPrevLimit(num: Double, amount: Double){
        limit = num
        this.amount = amount

    }

    fun getNewLimit(){
        viewModelScope.launch{
            try{
                if(limit < 1){
                    _uiState.value = GeneralUiState.Error("Cantidad invalida")
                } else if(limit < amount){
                        _uiState.value = GeneralUiState.Error("Limite es menor a los gastos de la categoria")
                } else {
                    _uiState.value = GeneralUiState.Success
                }
            } catch(e: Exception){
                _uiState.value = GeneralUiState.Error("Ingrese un numero")
            }
        }
    }

}