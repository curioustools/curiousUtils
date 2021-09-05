package work.curioustools.jetpack_lifecycles

import androidx.lifecycle.MutableLiveData


fun <T> MutableLiveData<T>.postUpdate(data:T? ,postingFromMainThread:Boolean = false){
    if (postingFromMainThread) value = data
    else postValue(data)
}