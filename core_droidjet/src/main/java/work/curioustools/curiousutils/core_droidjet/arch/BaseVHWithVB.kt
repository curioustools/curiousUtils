package work.curioustools.curiousutils.core_droidjet.arch

import androidx.viewbinding.ViewBinding

abstract class BaseVHWithVB(binding: ViewBinding) : BaseVH(binding.root) {
    abstract fun bindData(model: BaseListModel, payload: Any? = null)//todo move to package
}