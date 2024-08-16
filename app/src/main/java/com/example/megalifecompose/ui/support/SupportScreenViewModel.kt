package com.example.megalifecompose.ui.support

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.megalifecompose.App
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SupportScreenViewModel : ViewModel() {

    private val _photos = MutableStateFlow<List<Uri>>(listOf())
    val photos: StateFlow<List<Uri>> = _photos.asStateFlow()

    init {
        App.getAppComponent().inject(this)
    }

    fun addPhoto(uri: Uri) {
        _photos.value = _photos.value + listOf(uri)
    }

}