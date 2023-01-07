package rating_test.database

import kotlinx.coroutines.runBlocking
import rating_test.database.entities.User
import rating_test.database.entities.UserRatingTable
import rating_test.database.entities.UserTable
import ru.astrainteractive.astralibs.orm.DBConnection
import ru.astrainteractive.astralibs.orm.Database
import java.util.*
import kotlin.test.*

class DatabaseTests {
    private lateinit var databaseV2: Database
    val randomUser: User
        get() = runBlocking {
            val uuid = UUID.randomUUID().toString()
            val id = UserTable.insert(databaseV2) {
                this[UserTable.uuid] = uuid
                this[UserTable.lastUpdated] = System.currentTimeMillis()
            }
            return@runBlocking UserTable.find(databaseV2,constructor = User) {
                UserTable.id.eq(id)
            }.first()
        }

    @BeforeTest
    fun setup(): Unit = runBlocking {
        databaseV2 = Database()
        databaseV2.openConnection("dbv2_2.db",DBConnection.SQLite)
        UserTable.create(databaseV2)
        UserRatingTable.create(databaseV2)
    }

    @AfterTest
    fun destruct(): Unit = runBlocking {
        databaseV2.closeConnection()
    }

    @Test
    fun `Test more expression`(){
        val timeMillis = System.currentTimeMillis()
        randomUser
        val users = UserTable.find(databaseV2,constructor = User){
            UserTable.lastUpdated.more(timeMillis-100)
        }
        assertEquals(1,users.size)

        randomUser

    }
}