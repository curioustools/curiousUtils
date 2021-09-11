package work.curioustools.curiousutils.core_droidjet.arch

import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import work.curioustools.curiousutils.core_droidjet.vb_helpers.VBHolder
import work.curioustools.curiousutils.core_droidjet.vb_helpers.VBHolderImpl

abstract class BaseCommonActivityVB<VB: ViewBinding>: BaseCommonActivity(), VBHolder<VB> by VBHolderImpl() {
    override fun onCreate(savedInstanceState: Bundle? ) {
        super.onCreate(savedInstanceState)
        getBindingForComponent(layoutInflater).setContentViewFor(this)
        setup()

    }
    abstract fun getBindingForComponent(layoutInflater: LayoutInflater):VB
    abstract fun setup()
}


