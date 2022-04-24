package core

import org.koin.core.component.KoinComponent
import provider.ServerProvider

class TestServerProvider : KoinComponent, ServerProvider {
    private var host: String = "localhost"
    private var port: Int = 80

    fun setHost(host: String) {
        this.host = host
    }

    fun setPort(port: Int) {
        this.port = port
    }

    override fun getBaseUrl(): String {
        return "http://${host}:${port}"
    }

}
