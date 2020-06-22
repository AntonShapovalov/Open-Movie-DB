package concept.omdb.ui.activity

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Provide Rx java schedulers
 * Test version overrides it to run all tasks on the same thread
 */
@Singleton
open class SchedulerProvider @Inject constructor() {

    open val io: Scheduler get() = Schedulers.io()

    open val main: Scheduler get() = AndroidSchedulers.mainThread()

}