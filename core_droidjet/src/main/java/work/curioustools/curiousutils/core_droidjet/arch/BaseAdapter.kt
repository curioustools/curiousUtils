package work.curioustools.curiousutils.core_droidjet.arch

import androidx.recyclerview.widget.RecyclerView
import work.curioustools.curiousutils.core_droidjet.extensions.appendPair
import work.curioustools.curiousutils.core_droidjet.extensions.log
import work.curioustools.curiousutils.core_droidjet.extensions.logString
import work.curioustools.curiousutils.core_droidjet.extensions.logit
import java.lang.StringBuilder


abstract class BaseAdapter<VH : BaseVH> : BaseAdapterListHelpers<VH>() {
    open var enabledLogs = true

    open fun onBindVH(holder: VH, position: Int, payloads: MutableList<Any>? = null) {
        logBindData(holder, position, payloads)
        holder.bindData(getEntryAt(position), payloads)
    }

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
        logVH("onViewRecycled",holder)
        super.onViewRecycled(holder)
    }

    override fun onFailedToRecycleView(holder: VH): Boolean {
        logVH("onFailedToRecycleView",holder)
        return super.onFailedToRecycleView(holder)
    }

    override fun onViewAttachedToWindow(holder: VH) {
        logVH("onViewAttachedToWindow",holder)
        super.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: VH) {
        logVH("onViewDetachedFromWindow",holder)
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



    private fun logBindData(holder: VH, position: Int, payloads: MutableList<Any>? = null) {
        if (!enabledLogs) return
        logit("called from","bindData")
        logit("bindData position", position)
        logit("payloads", payloads)
        logVH("bindData",holder)

    }
    private fun logVH(callFunction:String , holder: VH) {
        if (!enabledLogs) return
        kotlin.runCatching {
            StringBuilder().run {
                appendPair("called from",callFunction)
                appendPair("vh", holder.hashCode())
                appendPair("adapterPosition", holder.adapterPosition)
                appendPair("isRecyclable", holder.isRecyclable)
                appendPair("itemId", holder.itemId)
                appendPair("itemViewType", holder.itemViewType)
                appendPair("layoutPosition", holder.layoutPosition)
                appendPair("oldPosition", holder.oldPosition)
                appendPair("position(deprecated)", holder.position)
            }.toString().log(">")

        }
    }
}


