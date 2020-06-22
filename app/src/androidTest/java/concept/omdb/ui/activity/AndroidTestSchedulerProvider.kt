package concept.omdb.ui.activity

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class AndroidTestSchedulerProvider : SchedulerProvider() {

    private val trampoline = Schedulers.trampoline()

    override val io: Scheduler get() = trampoline

    override val main: Scheduler get() = trampoline

}