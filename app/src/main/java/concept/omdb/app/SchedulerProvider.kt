package concept.omdb.app

import io.reactivex.Scheduler

/**
 * Provide Rx java schedulers
 * Test version overrides it to run all tasks on the same thread
 */
interface SchedulerProvider {

    val io: Scheduler

    val main: Scheduler

}