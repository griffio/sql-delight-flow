import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.example.Database
import com.example.Session
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.time.LocalDateTime

val driver = JdbcSqliteDriver("jdbc:sqlite:sessions.db")
val database = Database(driver)

fun selectAll(database: Database): Flow<List<Session>> {
    return database.appQueries.selectSessions()
        .asFlow()
        .mapToList(Dispatchers.IO)
}

suspend fun main() = coroutineScope {

    Database.Schema.create(driver) // create table in local sessions.db

    database.appQueries.deleteSessions()

// Reactive Query will print new rows as they are inserted

// Use onEach and launchIn
//    val job = selectAll(database).onEach {
//        println(it.joinToString("\n"))
//    }.launchIn(this)

// or Use launch and collect
    val job= launch {
        selectAll(database).collect {
            println(it.joinToString("\n"))
        }
    }

    (1L .. 10L).forEach { id ->
        database.appQueries.insertSession(id, 2 * id, "${LocalDateTime.now()}", "${LocalDateTime.now()}")
        delay(1000)
    }

    delay(2000)

    job.cancel()

    driver.close()

}

