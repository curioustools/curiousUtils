package work.curioustools.curiousutils.core_droidjet.extensions

fun StringBuilder.appendPair(key:String, value:Any,mid:String = ":",end:String = "|"){
    append("$key $mid $value $end")
}