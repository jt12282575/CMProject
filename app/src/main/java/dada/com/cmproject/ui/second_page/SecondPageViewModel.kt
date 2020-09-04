package dada.com.cmproject.ui.second_page

import androidx.lifecycle.*
import dada.com.cmproject.model.Data
import dada.com.cmproject.repository.Repository
import dada.com.cmproject.ui.BaseViewModel
import dada.com.cmproject.util.Resource

class SecondPageViewModel(
    private val repo:Repository
) :BaseViewModel(){

    private val _loadData = MutableLiveData<Boolean>()

    init {
        fetchData()
    }

    fun fetchData(){
        _loadData.value = true
    }

    val data:LiveData<Resource<List<Data>>> = Transformations.switchMap(_loadData){
        launchDataLoad {
            repo.fetchData()
        }
    }



}

object SecondPageVMFactory : ViewModelProvider.Factory {

    private val repo = Repository()

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return SecondPageViewModel(repo) as T
    }
}