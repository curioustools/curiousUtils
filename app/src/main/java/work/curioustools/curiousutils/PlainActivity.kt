package work.curioustools.curiousutils

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import work.curioustools.curiousutils.core_droidjet.extensions.configureSystemBar
import work.curioustools.curiousutils.core_droidjet.extensions.extras.SystemBarsConfig
import work.curioustools.curiousutils.core_droidjet.extensions.extras.SystemBarsConfig.*

class PlainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plain)
    }

    override fun onResume() {
        super.onResume()
        configureSystemBar(configDRL)
    }

    fun onClick(v: View?) {
        when (v?.id) {
            R.id.configDRL -> configureSystemBar(configDRL)
            R.id.configLRD -> configureSystemBar(configLRD)
            R.id.configDDD -> configureSystemBar(configDDD)
            R.id.configFSI -> configureSystemBar(configFSI)
            R.id.configFSN -> configureSystemBar(configFSN)
            R.id.changeTint -> configureSystemBar(
                SystemBarsConfig(SystemBarType.SHOW_ALL_DEFAULT, R.color.dark1, R.color.light1),
                if (++count % 2 == 0) R.color.dark1 else R.color.light1
            )
        }
    }

    var count = 1


    val configDRL = SystemBarsConfig(
        type = SystemBarType.SHOW_ALL_DEFAULT,
        statusBarColorRes = R.color.dark1,
        navBarDividerColorRes = R.color.red,
        navBarColorRes = R.color.light1
    )

    val configLRD = SystemBarsConfig(
        type = SystemBarType.SHOW_ALL_DEFAULT,
        statusBarColorRes = R.color.light1,
        navBarDividerColorRes = R.color.red,
        navBarColorRes = R.color.dark1
    )

    val configDDD = SystemBarsConfig(
        type = SystemBarType.SHOW_ALL_DEFAULT,
        statusBarColorRes = R.color.dark1,
        navBarDividerColorRes = R.color.dark1,
        navBarColorRes = R.color.dark1
    )

    val configFSN = SystemBarsConfig(SystemBarType.FULL_SCREEN_NO_ICONS)
    val configFSI = SystemBarsConfig(SystemBarType.FULL_SCREEN_WITH_ICONS)


}