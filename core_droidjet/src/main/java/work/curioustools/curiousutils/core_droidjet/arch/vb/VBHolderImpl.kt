package work.curioustools.curiousutils.core_droidjet.arch.vb

import androidx.viewbinding.ViewBinding

/*
 * an implementation of VBHolder which will be providing the implementation logic to each activity/fragment
 * since the main implementaion logic could only run after onCreate/ onCreateView, this class only provides
 * us the benefit of not writing `override val bindingHolder: Binding? = null` multiple times
 * */
class VBHolderImpl<VB : ViewBinding> : VBHolder<VB> {
    override var bindingHolder: VB? = null
}