import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.example.Database
import com.example.Session
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.LocalDateTime

val driver = JdbcSqliteDriver("jdbc:sqlite:sessions.db")
val database = Database(driver)

fun selectAll(database: Database): Flow<List<Session>> {
    return database.appQueries.selectSessions()
        .asFlow()
        .mapToList(Dispatchers.IO)
}

suspend fun main() = coroutineScope {

    Database.Schema.create(driver).value // create table in local sessions.db

    database.appQueries.deleteSessions()

    // Reactive Query will print new rows as they are inserted
    val job = selectAll(database).onEach {
        println(it.joinToString("\n"))
    }.launchIn(this)

    (1L .. 10L).forEach { id ->
        database.appQueries.insertSession(id, 2 * id, "${LocalDateTime.now()}", "${LocalDateTime.now()}")
        delay(1000)
    }

    delay(2000)

    job.cancel()

    driver.close()

}

