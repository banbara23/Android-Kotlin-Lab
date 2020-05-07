package com.ikemura.android_kotlin_lab.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikemura.android_kotlin_lab.repository.ISampleRepository
import com.ikemura.android_kotlin_lab.repository.SampleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    // 状態（viewModel専用）
    private var _state = MutableLiveData<ScreenState>()
    private val repository: ISampleRepository = SampleRepository()

    // 外部公開用
    val state = Transformations.distinctUntilChanged(_state)

    // データ読み込み
    fun load() {
        _state.value = ScreenState.Loading // ローディング表示
        viewModelScope.launch(Dispatchers.Main) {
            try {
                // API通信処理
                val response = repository.load()
                _state.value = ScreenState.Data(response) // APIレスポンスを表示
            } catch (e: Exception) {
                _state.value = ScreenState.Error // エラー
            }
        }
    }
}
