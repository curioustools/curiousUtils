package work.curioustools.curiousutils.core_droidjet.arch

import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import work.curioustools.curiousutils.core_droidjet.IsTested

@IsTested("yes",addedExactFixComments = true,needsImprovement = true)
//remove this class. its useless
abstract class BaseCommonActivity: AppCompatActivity(){
    val activityHandler: Handler by lazy {
        val looper = Looper.getMainLooper()
        Handler(looper)
    }
}

