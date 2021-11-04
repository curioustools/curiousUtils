package work.curioustools.curiousutils.core_droidjet.arch

import android.util.Log
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapterListHelpers<VH : BaseVH> : RecyclerView.Adapter<VH>() {
    private val entries = mutableListOf<BaseListModel>()

    override fun getItemCount() = entries.size

    fun addEntry(newEntry: BaseListModel) {
        entries.add(newEntry)
        notifyItemInserted(entries.lastIndex)
    }

    fun addEntries(newEntries: List<BaseListModel>) {
        val initialLastIdx = entries.lastIndex
        entries.addAll(newEntries)
        notifyItemRangeInserted(initialLastIdx, entries.size)
    }

    fun getEntryAt(idx: Int): BaseListModel? {
        return entries.getOrNull(idx)
    }

    fun getAllEntries(): List<BaseListModel> {
        return entries.toList()
    }


    fun updateEntry(pos: Int, newEntry: BaseListModel, payload: Any? = null) {
        if (pos !in entries.indices) return
        entries.add(pos, newEntry)
        notifyItemChanged(pos, payload)
    }

    fun updateAllEntries(newEntries: List<BaseListModel>, payload: Any? = null) {
        removeAllEntries(false)
        entries.addAll(newEntries)
        notifyItemRangeChanged(0, entries.size, payload)
    }


    fun removeEntryAt(idx: Int) {
        if (idx !in entries.indices) return
        entries.removeAt(idx)
        notifyItemRemoved(idx)
    }

    fun removeEntriesFrom(startingIdx: Int, endingIdx: Int) {
        //note: Both indices are included.
        if (startingIdx !in entries.indices) return
        val subList = entries.subList(startingIdx, endingIdx + 1)
        entries.removeAll(subList)

        try {
            val count = endingIdx - startingIdx + 1
            notifyItemRangeRemoved(startingIdx, count)
        }
        catch (t: Throwable) {
            Log.e("ADP", "adp function crashed! removeEntriesFrom: $startingIdx - $endingIdx , size = ${entries.size}", t)
            notifyDataSetChanged()
        }
    }

    fun removeAllEntries(notifyDataSetChanged: Boolean = true) {
        entries.clear()
        if (notifyDataSetChanged) notifyDataSetChanged()
    }



}