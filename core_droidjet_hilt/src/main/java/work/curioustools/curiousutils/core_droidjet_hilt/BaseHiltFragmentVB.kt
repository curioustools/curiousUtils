package work.curioustools.curiousutils.core_droidjet_hilt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import dagger.hilt.android.AndroidEntryPoint
import work.curioustools.curiousutils.core_droidjet.arch.BaseCommonFragment
import work.curioustools.curiousutils.core_droidjet.vb_helpers.VBHolder
import work.curioustools.curiousutils.core_droidjet.vb_helpers.VBHolderImpl


@AndroidEntryPoint
abstract class BaseHiltFragment : BaseCommonFragment()

abstract class BaseHiltFragmentVB<VB : ViewBinding> :
    BaseHiltFragment(),
    VBHolder<VB> by VBHolderImpl() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return getBindingForThisComponent(inflater, container, savedInstanceState).registeredRoot(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    abstract fun getBindingForThisComponent(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): VB
    abstract fun setup()
}
