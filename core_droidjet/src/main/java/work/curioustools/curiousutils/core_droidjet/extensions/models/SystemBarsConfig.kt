package work.curioustools.curiousutils.core_droidjet.extensions.models

import androidx.annotation.ColorRes
import androidx.annotation.Keep


@Keep
data class SystemBarsConfig(
    val type: SystemBarType = SystemBarType.SHOW_ALL_DEFAULT,
    @ColorRes val statusBarColorRes: Int? = null,
    @ColorRes val navBarColorRes: Int? = null,
    @ColorRes val navBarDividerColorRes: Int? = null,
){

    @Keep
    enum class SystemBarType {
        /* Default mode. i.e bar will show with usual color and content will reside inside the bars*/
        SHOW_ALL_DEFAULT,

        /*Show in full screen mode mode. i.e with icons  and content will ignore the bars*/
        FULL_SCREEN_WITH_ICONS,

        /*Show in immersive mode. i.e without icons and content will ignore the bars. icons will be visible on swiping down*/
        FULL_SCREEN_NO_ICONS
    }

}
