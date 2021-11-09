package work.curioustools.curiousutils.core_droidjet.extensions


fun relaxedError(msg:String, relaxed:Boolean = false) {
    if(!relaxed) error(msg)
}