package work.curioustools.curiousutils.core_droidjet.extensions

import android.os.Bundle
import android.util.Log

fun Bundle?.logBundle(tag: String = "BUNDLE>>",  initialText:String? = null) {
    this ?: kotlin.run {

        logit(value =  "{}", tag = tag)
        return
    }
    val initmsg = if(initialText.isNullOrBlank()) "" else initialText
    logit( value = "$initmsg \n\t{")
    this.keySet().forEach {
       logString("\t\t $it : ${get(it).toString()}")
    }
   logString("\t{")
}
