package work.curioustools.curiousutils.core_droidjet.arch

import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

class SimpleListAdapterVB(
    private val getBinding: (parent:ViewGroup) -> ViewBinding,
    private val onBind: (binding: ViewBinding, item: BaseListModel) -> Unit,
    entries:List<BaseListModel>
) : BaseAdapter<SimpleVh>() {
    init {
        addEntries(entries)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SimpleVh(getBinding.invoke(parent))
    override fun onBindVH(holder: SimpleVh, position: Int, payloads: MutableList<Any>?) = getEntryAt(position)?.let { onBind.invoke(holder.binding,it) }?:Unit
}
