package work.curioustools.curiousutils.core_droidjet.arch

import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding

import work.curioustools.curiousutils.core_droidjet.arch.vb.VBHolder
import work.curioustools.curiousutils.core_droidjet.arch.vb.VBHolderImpl

//remove this class. its useless
abstract class BaseCommonActivityVB<VB: ViewBinding>: BaseCommonActivity(), VBHolder<VB> by VBHolderImpl() {
    override fun onCreate(savedInstanceState: Bundle? ) {
        super.onCreate(savedInstanceState)
        getBindingForComponent(layoutInflater).setContentViewVB(this)
        setup()

    }
    abstract fun getBindingForComponent(layoutInflater: LayoutInflater):VB
    abstract fun setup()
}


