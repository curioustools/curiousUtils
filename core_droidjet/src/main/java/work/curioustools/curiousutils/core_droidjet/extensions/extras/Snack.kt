package work.curioustools.curiousutils.core_droidjet.extensions.extras

import androidx.annotation.Keep
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

@Keep
data class Snack(
    val msg: String = "",
    @StringRes val msgRes: Int = -1,
    val length: Int = Snackbar.LENGTH_SHORT
)