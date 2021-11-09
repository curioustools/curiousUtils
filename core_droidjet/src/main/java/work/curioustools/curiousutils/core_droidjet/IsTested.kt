package work.curioustools.curiousutils.core_droidjet

import androidx.annotation.Keep

@Keep
annotation class IsTested(
    val isWorking:String = "" ,
    val addedExactFixComments:Boolean = false,
    val needsImprovement:Boolean = true,
    val canBeScaled:Boolean = true,
)
