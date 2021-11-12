package work.curioustools.curiousutils.core_droidjet.extensions


fun relaxedError(msg:String, relaxed:Boolean = true) {
    if(relaxed) logit("error",msg) else error(msg)
}
