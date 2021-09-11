package work.curioustools.curiousutils.core_droidjet.vb_helpers

import androidx.viewbinding.ViewBinding


class VBHolderImpl<VB : ViewBinding> : VBHolder<VB> {
    override var binding: VB? = null

}
