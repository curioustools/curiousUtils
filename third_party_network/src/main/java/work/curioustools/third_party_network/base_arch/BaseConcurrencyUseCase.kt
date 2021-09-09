package work.curioustools.third_party_network.base_arch

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class BaseConcurrencyUseCase<REQUEST,RESP>{
    private val job = Job()
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)

    val liveData = MutableLiveData<RESP>()

   abstract suspend fun  getRepoCall(param: REQUEST) : RESP

    fun requestForData(param:REQUEST){
        scope.apply {
            launch(Dispatchers.IO +job){
                val result: RESP? =  getRepoCall(param)
                result?.let { liveData.postValue(it) }
            }
        }
    }


}