package com.example.missionarchitect.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.missionarchitect.domain.repository.UserRepository
import com.example.missionarchitect.domain.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

@HiltViewModel
class HomeViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {
    // 1. Mutable State: Private so the UI layer cannot modify it directly.
    private val _uiState= MutableStateFlow(HomeUiState())
    // 2. Immutable State: Public for the Compose UI to observe.
    val uiState : StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadUser()
    }

    public fun loadUser()
    {
            viewModelScope.launch {
                _uiState.value = HomeUiState(isLoading = true)

                    when(val result =userRepository.fetchUsers())
                    {
                        is NetworkResult.Success->{ _uiState.value= HomeUiState(users = result.data?:emptyList(), isLoading = false)
                        }
                        is NetworkResult.Error->{ _uiState.value = HomeUiState(error = result.message, isLoading = false)
                        }
                        is NetworkResult.Loading->{_uiState.value= HomeUiState(isLoading = true)
                        }

                    }


            }
    }
}