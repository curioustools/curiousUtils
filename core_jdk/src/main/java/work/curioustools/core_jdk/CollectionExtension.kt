package work.curioustools.core_jdk

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import java.util.*


fun <T> List<T?>.summary(listName:String = "list="):String{
    return if (size > 1) {
        val f = getOrNull(0)
        val l = getOrNull(lastIndex)
        "$listName ( $size items)[$f\t..\t $l]"
    }
    else this.toString()
}


fun <T> List<T?>.hasDuplicates(isItemSame:(T?,T?)->Boolean):Boolean{
    var duplicatesIndices :MutableList<Int>

    for (i in this.indices){
        duplicatesIndices = mutableListOf(i)
        for (j in i+1 until this.size){
            if(isItemSame(this[i],this[j])) duplicatesIndices.add(j)
        }

        if(duplicatesIndices.size>1){
            println("found duplicates for item ${this[i]} at indices : $duplicatesIndices")
            return true
        }

    }
    return false
}

fun <T> Iterable<T>.toJavaArrayList(): ArrayList<T> {
    val result = ArrayList<T>()
    this.forEach { result.add(it) }
    return result
}



