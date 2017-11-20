import assertk.assert
import assertk.assertions.isEqualTo
import io.reactivex.Observable
import it.modularity.events.DojoEventsProvider
import it.modularity.events.common.model.DojoEvent
import it.modularity.events.common.model.DojoLocation
import it.modularity.events.common.model.DojoOrganizer
import it.modularity.events.common.provider.DojoEventProvider
import org.junit.Assert.assertThat
import org.junit.Test
import java.time.Instant
import java.util.*

class CoderDojoProviderTest {

    class FakeEventProvider(private val events: List<DojoEvent>) : DojoEventProvider() {
        override fun provide(query: String, latitude: Double, longitude: Double, range: Double): Observable<DojoEvent> = Observable.fromIterable(events)
    }

    class FailingEventProvider : DojoEventProvider() {
        override fun provide(query: String, latitude: Double, longitude: Double, range: Double): Observable<DojoEvent> = Observable.error(RuntimeException("Generic error"))
    }

    private fun fakeEvent(endTime: Long = Instant.now().plusSeconds(60 * 60 * 24).toEpochMilli()): DojoEvent = DojoEvent(
            title = "coderdojo-event-".plus(UUID.randomUUID().toString()),
            description = "a coderdojo-event",
            logo = "http://cdn.it/logo.png",
            icon = "http://cdn.it/icon.png",
            ticketUrl = "http://codertickets.it/ticket?id=42",
            startTime = Instant.ofEpochMilli(endTime).minusSeconds(60 * 60).toEpochMilli(),
            endTime = endTime,
            capacity = 10, participants = 5,
            location = DojoLocation("a street", "address", "city", "IT"),
            organizer = DojoOrganizer(id = "1", platform = "fake"),
            free = true
    )

    @Test
    fun testMergeResult() {
        val biProvider = FakeEventProvider(events = listOf(fakeEvent(), fakeEvent()))
        val singleProvider = FakeEventProvider(events = listOf(
                fakeEvent(),
                fakeEvent(endTime = Instant.now().minusSeconds(100).toEpochMilli())
        ))
        val provider = DojoEventsProvider(providers = listOf(singleProvider, biProvider))
        provider.provide("", 10.0, 11.1, 120.0)
                .test().await()
                .assertNoErrors()
                .assertComplete()
                .assertValueCount(1)
                .assertValue { it.size == 3 }
    }

    @Test
    fun testEmptyProvider() {
        val emptyProvider = FakeEventProvider(events = listOf())
        val singleProvider = FakeEventProvider(events = listOf(fakeEvent()))
        val provider = DojoEventsProvider(providers = listOf(singleProvider, emptyProvider))
        val events = provider.provide("", 10.0, 11.1, 120.0).test().await()
        events.assertNoErrors()
                .assertValueCount(1)

    }

    @Test()
    fun testFailingProvider() {
        val failing = FailingEventProvider()
        val singleProvider = FakeEventProvider(events = listOf(fakeEvent()))
        val provider = DojoEventsProvider(providers = listOf(singleProvider, failing))
        val events = provider.provide("", 10.0, 11.1, 120.0).test().await()
        events.assertError(RuntimeException::class.java)
    }

}