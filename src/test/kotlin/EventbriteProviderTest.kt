import assertk.assert
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import com.github.tomakehurst.wiremock.junit.WireMockRule
import it.modularity.events.eventbrite.EventbriteProvider
import it.modularity.events.eventbrite.api.EventbriteService
import it.modularity.events.eventbrite.response.Event
import it.modularity.events.eventbrite.response.EventbriteSearchResult
import it.modularity.events.eventbrite.response.Venue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import util.Resource
import util.WireUtil


class EventbriteProviderTest {

    @Rule
    @JvmField
    var wireMockRule = WireMockRule(9090)

    private lateinit var venue: Venue
    private lateinit var events: Array<Event>

    private val provider = EventbriteProvider(token = "test-token", api = EventbriteService("http://localhost:9090/").api)

    @Before
    fun setUp() {

        WireUtil.answer(
                url = "/v3/events/search/?token=test-token&q=test-dojo&location.latitude=10.0&location.longitude=11.0&location.within=150km&sort_by=distance&price=free",
                response = Resource.read("/eventbrite_search_response.json")
        )

        WireUtil.answer(
                url = "/v3/venues/21933668?token=test-token",
                response = Resource.read("/eventbrite_venue_response.json")
        )

        WireUtil.answer(
                url = "/v3/events/search/?token=test-token&q=test-error-500&location.latitude=10.0&location.longitude=11.0&location.within=150km&sort_by=distance&price=free",
                response = "500 Internal Error",
                code = 500
        )

        this.venue = Resource.readObject("/eventbrite_venue_response.json")
        val search: EventbriteSearchResult = Resource.readObject("/eventbrite_search_response.json")

        this.events = search.events!!
        assert(this.events).hasSize(2)
    }

    @Test
    fun testProvider() {

        val results = provider.provide(query = "test-dojo", latitude = 10.0, longitude = 11.0, range = 150.0)
                .test()

        assert(results.valueCount()).isEqualTo(this.events.size)

        results.assertNoErrors().assertComplete()

        this.events.forEachIndexed({ i, e ->
            results.apply {
                assertValueAt(i, { it.title == e.name.text })
                assertValueAt(i, { it.description == e.description.text })
                assertValueAt(i, { it.capacity == e.capacity?.toInt() })
                assertValueAt(i, { it.participants == null })
                assertValueAt(i, { it.location.name == venue.name })
                assertValueAt(i, { it.location.latitude == venue.address.latitude?.toDouble() })
                assertValueAt(i, { it.location.longitude == venue.address.longitude?.toDouble() })
                assertValueAt(i, { it.location.postalCode == venue.address.postal_code })
                assertValueAt(i, { it.location.address == venue.address.address_1 })
            }
        })
    }

    @Test
    fun testProviderFail500() {
        this.provider.provide(query = "test-error-500", latitude = 10.0, longitude = 11.0, range = 150.0)
                .test()
                .assertError(RuntimeException::class.java)
    }

}