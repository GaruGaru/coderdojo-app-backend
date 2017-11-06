package util

import com.github.tomakehurst.wiremock.client.WireMock

class WireUtil {
    companion object {
        fun answer(url: String, response: String, code: Int = 200) {
            WireMock.stubFor(WireMock.get(WireMock.urlEqualTo(url))
                    .willReturn(WireMock.aResponse()
                            .withStatus(code)
                            .withBody(response)))
        }
    }

}