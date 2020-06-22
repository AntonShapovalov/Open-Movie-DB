package concept.omdb.app

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Provide Rx java schedulers
 *
 * Test version uses [TestScheduler] instead
 */
@Singleton
open class SchedulerProvider @Inject constructor() {

    open val io: Scheduler get() = Schedulers.io()

    open val main: Scheduler get() = AndroidSchedulers.mainThread()

}