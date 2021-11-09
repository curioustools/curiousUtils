package work.curioustools.curiousutils.core_droidjet.arch.customs

import android.view.ViewGroup
import androidx.annotation.Keep
import androidx.viewbinding.ViewBinding
import work.curioustools.curiousutils.core_droidjet.IsTested
import work.curioustools.curiousutils.core_droidjet.arch.BaseAdapter
import work.curioustools.curiousutils.core_droidjet.arch.BaseListModel
import work.curioustools.curiousutils.core_droidjet.arch.BaseVH

// a quick, easy to use option for showing list. just provide the initialiser and how to set data and your are good to go
@IsTested("not tested",addedExactFixComments = false)
class SimpleListAdapter<T : ViewBinding>(
    private val getBinding: (parent: ViewGroup) -> T,
    private val onBind: (binding: T, item: BaseListModel) -> Unit,
    entries: List<BaseListModel>,
) : BaseAdapter<SimpleListAdapter.SimpleVh<T>>() {
    init {
        addEntries(entries)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleVh<T> {
        return SimpleVh(getBinding.invoke(parent))
    }
    override fun onBindVH(holder: SimpleVh<T>, position: Int, payloads: MutableList<Any>?) {
        getEntryAt(position)?.let { onBind.invoke(holder.binding, it) } ?: Unit
        //super.onBindVH(holder, position, payloads)
    }


    @Keep
    class SimpleVh<VB : ViewBinding>(val binding: VB) : BaseVH(binding.root) {
        override fun bindData(data: BaseListModel?, payloads: MutableList<Any>?) {}
    }
}
