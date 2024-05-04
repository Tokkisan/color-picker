package com.example.tes1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class MyViewModel: ViewModel() {

    private val prefs = MyPreferencesRepository.get()

    fun saveInputString(s: String, index: Int) {
        viewModelScope.launch {
            prefs.saveInputString(s, index)
        }
    }
    fun saveInputBool(s: Boolean, index: Int) {
        viewModelScope.launch {
            prefs.saveInputBool(s, index)
        }
    }

    fun saveInputInt(s: Int, index: Int) {
        viewModelScope.launch {
            prefs.saveInputInt(s, index)
        }
    }


    fun LoadInputs(act: MainActivity) {
        viewModelScope.launch {
            prefs.red_text.collectLatest {
                act.redValue.text = it.toString()
            }
        }

        viewModelScope.launch {
            prefs.green_text.collectLatest {
                act.greenValue.text = it.toString()
            }
        }

        viewModelScope.launch {
            prefs.blue_text.collectLatest {
                act.blueValue.text = it.toString()
            }
        }

        viewModelScope.launch {
            prefs.red_switch.collectLatest {
                act.redSwitch.isChecked = it
            }
        }

        viewModelScope.launch {
            prefs.green_switch.collectLatest {
                act.greenSwitch.isChecked = it
            }
        }

        viewModelScope.launch {
            prefs.blue_switch.collectLatest {
                act.blueSwitch.isChecked = it
            }
        }

        viewModelScope.launch {
            prefs.red_progress.collectLatest {
                act.redBar.progress = it
            }
        }

        viewModelScope.launch {
            prefs.green_progress.collectLatest {
                act.greenBar.progress = it
            }
        }

        viewModelScope.launch {
            prefs.blue_progress.collectLatest {
                act.blueBar.progress = it
            }
        }


    }

}