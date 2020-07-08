package concept.omdb

import concept.omdb.data.repo.LocalDataSourceAndroidTest
import concept.omdb.ui.info.MovieInfoFragmentAndroidTest
import concept.omdb.ui.list.MovieListFragmentAndroidTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

/**
 * Test suite to run all Android instrumental tests together in defined order
 */
@RunWith(Suite::class)
@Suite.SuiteClasses(
    // save and get data from local storage using GreenDao SQLite DB (!affects device data!)
    LocalDataSourceAndroidTest::class,
    // espresso UI test for movies' list fragment using fake Repository
    MovieListFragmentAndroidTest::class,
    // espresso UI test for movie's info fragment using fake Repository
    MovieInfoFragmentAndroidTest::class
)
class AndroidInstrumentedTestSuite
