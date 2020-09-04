package dada.com.cmproject.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dada.com.cmproject.Global
import dada.com.cmproject.R
import dada.com.cmproject.util.Resource
import kotlinx.coroutines.Dispatchers

open class BaseViewModel: ViewModel() {
    private val _spinner = MutableLiveData<Boolean>()
    val spinner: LiveData<Boolean>
        get() = _spinner

    fun <T> launchDataLoad (block: suspend() -> T) =
        liveData(Dispatchers.IO) {
            _spinner.postValue(true)
            try {
                emit(Resource.success(data = block.invoke()))
            }catch (exception: Exception){
                emit(Resource.error(data = null, message = exception.message ?: Global.getContext().getString(
                    R.string.api_error)))
            }finally {
                _spinner.postValue(false)
            }
        }
}