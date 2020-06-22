package concept.omdb.app

import io.reactivex.Scheduler
import io.reactivex.schedulers.TestScheduler

class TestSchedulerProvider : SchedulerProvider() {

    private val testScheduler = TestScheduler()

    override val io: Scheduler get() = testScheduler

    override val main: Scheduler get() = testScheduler

}