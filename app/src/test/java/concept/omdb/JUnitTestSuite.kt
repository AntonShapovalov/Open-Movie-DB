package concept.omdb

import concept.omdb.data.repo.MovieRepositoryTest
import concept.omdb.data.repo.RemoteDataSourceTest
import concept.omdb.ui.info.MovieInfoViewModelTest
import concept.omdb.ui.list.MovieListViewModelTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

/**
 * Test suite to run all junit tests together in defined order
 */
@RunWith(Suite::class)
@Suite.SuiteClasses(
    // get data from remote open movie API using retrofit service (!real network test!)
    RemoteDataSourceTest::class,
    // Repository test using mock data sources
    MovieRepositoryTest::class,
    // movies' list view model test using fake Repository
    MovieListViewModelTest::class,
    // movie's info view model test using fake Repository
    MovieInfoViewModelTest::class
)
class JUnitTestSuite