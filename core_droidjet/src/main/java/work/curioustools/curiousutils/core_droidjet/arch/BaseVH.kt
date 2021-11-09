package work.curioustools.curiousutils.core_droidjet.arch

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import work.curioustools.curiousutils.core_droidjet.IsTested

@IsTested("yes",needsImprovement = true)
// add more overridable functions
abstract class BaseVH(v: View): RecyclerView.ViewHolder(v){
    abstract fun bindData(data: BaseListModel?, payloads: MutableList<Any>?)
}