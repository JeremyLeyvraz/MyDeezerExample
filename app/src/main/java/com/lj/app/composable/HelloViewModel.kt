package com.lj.app.composable

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * View model to get a text using [HelloUseCase].
 */
@HiltViewModel
class HelloViewModel @Inject constructor(): ViewModel() {
    fun getText() = "Jeremy"
}
