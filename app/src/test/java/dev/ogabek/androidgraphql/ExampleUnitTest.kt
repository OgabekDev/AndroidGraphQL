package dev.ogabek.androidgraphql

import dev.ogabek.androidgraphql.network.GraphQL
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun checkApi() {
        val a = GraphQL.get().query(UsersLIstQuery(10))
        assertNotNull(a)
    }

}