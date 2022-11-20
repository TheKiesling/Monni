package com.example.monni.ui

import android.view.LayoutInflater
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monni.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject


@HiltViewModel
class NotificationsDialogViewModel @Inject constructor(): ViewModel() {

    private lateinit var args: List<String>

    private val _uiState: MutableStateFlow<NotificationsDialogUiState> =
        MutableStateFlow(NotificationsDialogUiState.Default)
    val uiState: StateFlow<NotificationsDialogUiState> = _uiState

    fun setArgs(params: List<String>){
        args = params
    }

    fun returnData(){
        viewModelScope.launch{
            if(args[0].isEmpty() && args[1].isEmpty() && args[2].isEmpty())
                _uiState.value = NotificationsDialogUiState.Default
            else if(args[0].length > 50){
                _uiState.value = NotificationsDialogUiState.Error("Mucho texto ermano", 1)
            }
            else if(args[1].length > 300){
                _uiState.value = NotificationsDialogUiState.Error("madre del amor ermoso", 2)
            } else{
                try{
                    LocalDate.of(args[2].substring(6).toInt(),
                        args[2].substring(3,5).toInt(),
                        args[2].substring(0,2).toInt())
                    _uiState.value = NotificationsDialogUiState.Success
                } catch(e: Exception){
                    _uiState.value = NotificationsDialogUiState.Error("Fecha invalida", 3)
                }

            }
        }
    }

}