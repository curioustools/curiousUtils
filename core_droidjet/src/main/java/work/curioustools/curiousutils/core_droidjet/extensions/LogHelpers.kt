package work.curioustools.curiousutils.core_droidjet.extensions


enum class LogType(val type:Int){
     VERBOSE (android.util.Log.VERBOSE),
     DEBUG (android.util.Log.DEBUG),
     INFO (android.util.Log.INFO),
     WARN (android.util.Log.WARN),
     ERROR ( android.util.Log.ERROR),
     PRINTLN (-1)
}

fun <T> T.log(name:String = "", tag:String = "LOG>>", type:LogType = LogType.ERROR){
   val msg =  "$name : $this "
    if(type!= LogType.PRINTLN) {
        android.util.Log.println(type.type,tag,msg)
    }
    else println(msg)
}

fun logString(str:String) = str.log()