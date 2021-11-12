package work.curioustools.third_party_exoplayer

import android.net.Uri
import android.util.Log


fun String.toUri(): Uri = Uri.parse(this)