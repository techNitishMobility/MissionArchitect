package com.example.missionarchitect.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.missionarchitect.domain.repository.UserRepository
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

    private fun loadUser()
    {
            viewModelScope.launch {
                _uiState.value = HomeUiState(isLoading = true)
                try {
                    val userList = userRepository.fetchUsers()
                    Log.d("Architect_Network_Log", "Response Received: $userList")
                    _uiState.value = HomeUiState(users = userList, isLoading = false)
                } catch (e: HttpException) {
                    val errorMessage = when(e.code()) {
                        404 -> "Resource not found (Check URL)"
                        500 -> "Server is down, try again later"
                        else -> "Something went wrong: ${e.message()}"
                    }
                    _uiState.value = HomeUiState(error = errorMessage, isLoading = false)
                } catch (e: IOException) {
                    _uiState.value = HomeUiState(error = "Network error, check your connection", isLoading = false)
                } catch (e: Exception) {
                    _uiState.value = HomeUiState(error = "An unexpected error occurred: ${e.localizedMessage}", isLoading = false)
                }
            }
    }
}