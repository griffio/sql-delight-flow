# SqlDelight 2.0

## Flow example

To consume a query as a Flow, add the coroutines extensions artifact as a dependency and use the extension functions it provides.

This flow emits the query result, and emits a new result every time the database changes for that query.

Shown below, the generated code will emit a change event for the mutated table (only the table name, not fields, are emitted)

```kotlin

 public fun insertSession(
    id: Long?,
    rate_limit: Long,
    created_at: String,
    updated_at: String,
  ) {
    driver.execute(-1_806_899_413, """
        |INSERT INTO session (id, rate_limit, created_at, updated_at)
        |    VALUES(?, ?, ?, ?)
        """.trimMargin(), 4) {
          bindLong(0, id)
          bindLong(1, rate_limit)
          bindString(2, created_at)
          bindString(3, updated_at)
        }
    notifyQueries(-1_806_899_413) { emit ->
      emit("session")
    }
  }
```
