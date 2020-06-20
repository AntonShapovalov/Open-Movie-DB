package concept.omdb.ui.activity

import androidx.fragment.app.FragmentActivity
import concept.omdb.app.OMDBApplication
import concept.omdb.di.AppComponent

val FragmentActivity.appComponent: AppComponent get() = (application as OMDBApplication).appComponent
