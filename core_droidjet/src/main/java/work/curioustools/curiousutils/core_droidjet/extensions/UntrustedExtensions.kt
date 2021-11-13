package work.curioustools.curiousutils.core_droidjet.extensions


import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

import com.google.android.material.bottomsheet.BottomSheetDialogFragment

fun AppCompatActivity.getStatusBarHeight(): Int {
    // should be used with activity because context has this method as abstract
    var result = 0
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = resources.getDimensionPixelSize(resourceId)
    }
    return result
}

fun BottomSheetDialogFragment.showSafeBottomSheet(fragmentManager: FragmentManager, s: String) {
    if (!fragmentManager.isDestroyed && !fragmentManager.isStateSaved)
        this.show(fragmentManager, s)
}

fun RecyclerView.swipeAbleItemDelete(
    onSwipeCallback: (viewHolder: RecyclerView.ViewHolder) -> Unit = {},
    getSwipeDirs: (recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) -> Int? = { _: RecyclerView, _: RecyclerView.ViewHolder -> null }
) {
    val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        override fun getSwipeDirs(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
            return getSwipeDirs(recyclerView, viewHolder) ?: super.getSwipeDirs(recyclerView, viewHolder)
        }

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return false
        }

        override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
            val itemView = viewHolder.itemView
            val itemHeight = itemView.bottom - itemView.top

            val backgroundColor = context.getColorDrawable( android.R.color.holo_red_dark)
            backgroundColor.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
            backgroundColor.draw(c)
            val deleteIcon = ContextCompat.getDrawable(context, android.R.drawable.ic_delete)
            val deleteIconTop = itemView.top + (itemHeight - deleteIcon!!.intrinsicHeight) / 2
            val deleteIconMargin = (itemHeight - deleteIcon.intrinsicHeight) / 2
            val deleteIconLeft = itemView.right - deleteIconMargin - deleteIcon.intrinsicWidth
            val deleteIconRight = itemView.right - deleteIconMargin
            val deleteIconBottom = deleteIconTop + deleteIcon.intrinsicHeight

            deleteIcon.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)
            deleteIcon.draw(c)
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            onSwipeCallback(viewHolder)
        }
    }

    ItemTouchHelper(itemTouchHelperCallback).apply {
        attachToRecyclerView(this@swipeAbleItemDelete)
    }
}