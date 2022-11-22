package com.example.monni.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalDialogViewModel @Inject constructor(): ViewModel(){

    private var newGoal: Double = 0.0
    private var currentSavings: Double = 0.0

    private val _uiState: MutableStateFlow<GeneralUiState> =
        MutableStateFlow(GeneralUiState.Default)
    val uiState: StateFlow<GeneralUiState> = _uiState

    fun setPrevLimit(num: Double, currentSavings: Double){
        newGoal = num
        this.currentSavings = currentSavings

    }

    fun getNewGoal(){
        viewModelScope.launch{
            try{
                if(newGoal < 1){
                    _uiState.value = GeneralUiState.Error("Cantidad invalida")
                } else if(newGoal < currentSavings){
                    _uiState.value = GeneralUiState.Error("Meta es menor a la cantidad de ahorros actual")
                } else {
                    _uiState.value = GeneralUiState.Success
                }
            } catch(e: Exception){
                _uiState.value = GeneralUiState.Error("Ingrese un numero")
            }
        }
    }
}