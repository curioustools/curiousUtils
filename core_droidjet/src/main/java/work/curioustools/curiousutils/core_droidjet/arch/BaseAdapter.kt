package work.curioustools.curiousutils.core_droidjet.arch

import androidx.recyclerview.widget.RecyclerView


abstract class BaseAdapter<VH : BaseVH> : BaseAdapterListHelpers<VH>(){
    abstract fun onBindVH(holder: VH, position: Int, payloads: MutableList<Any>? = null)

    override fun onBindViewHolder(holder: VH, position: Int, payloads: MutableList<Any>) {
        onBindVH(holder, position, payloads)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        onBindVH(holder, position)
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun setHasStableIds(hasStableIds: Boolean) {
        super.setHasStableIds(hasStableIds)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun onViewRecycled(holder: VH) {
        super.onViewRecycled(holder)
    }

    override fun onFailedToRecycleView(holder: VH): Boolean {
        return super.onFailedToRecycleView(holder)
    }

    override fun onViewAttachedToWindow(holder: VH) {
        super.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: VH) {
        super.onViewDetachedFromWindow(holder)
    }

    override fun registerAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
        super.registerAdapterDataObserver(observer)
    }

    override fun unregisterAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
        super.unregisterAdapterDataObserver(observer)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
    }
}


