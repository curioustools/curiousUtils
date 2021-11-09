package work.curioustools.curiousutils.core_droidjet.arch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import work.curioustools.curiousutils.core_droidjet.IsTested
import work.curioustools.curiousutils.core_droidjet.arch.vb.VBHolder
import work.curioustools.curiousutils.core_droidjet.arch.vb.VBHolderImpl

@IsTested("yes",addedExactFixComments = true,needsImprovement = true)
//remove this class. its useless
abstract class BaseCommonFragmentVB<VB : ViewBinding> :
    BaseCommonFragment(),
    VBHolder<VB> by VBHolderImpl() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return getBindingForThisComponent(inflater, container, savedInstanceState).getVBView(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    abstract fun getBindingForThisComponent(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): VB
    abstract fun setup()
}