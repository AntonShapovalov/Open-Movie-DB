package concept.omdb.ui.activity

import concept.omdb.app.SchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class TestSchedulers : SchedulerProvider {

    private val trampoline = Schedulers.trampoline()

    override val io: Scheduler get() = trampoline

    override val main: Scheduler get() = trampoline

}