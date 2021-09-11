package work.curioustools.core_jdk

import java.net.InetSocketAddress
import java.net.Socket

class NetworkUtils {
    companion object {
        fun isReceivingInternetPackets(timeout: Int = 2500): Boolean {
            // more info here https://stackoverflow.com/a/27312494/7500651

            val dnsPort = 53
            val googleIp = "8.8.8.8"
            return kotlin.runCatching {
                val socket = Socket()
                val inetAddress = InetSocketAddress(googleIp, dnsPort)
                socket.connect(inetAddress, timeout)
                socket.close()
                return@runCatching true

            }.getOrDefault(false)
        }
    }
}