package pl.komorowskidev.kotlinrest.configuration

import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import pl.komorowskidev.kotlinrest.properties.Users
import java.util.*
import kotlin.test.BeforeTest
import kotlin.test.Test

class UserDetailsManagerCreatorTest {

    private lateinit var creator: UserDetailsManagerCreator
    private lateinit var usersMock: Users

    @BeforeTest
    fun init(){
        usersMock = mock(Users::class.java)
        creator = UserDetailsManagerCreator(usersMock)
    }

    @Test
    fun shouldReturnManagerWithUsers(){
        //given:
        val user1 = "user1"
        val user2 = "user2"
        val properties = Properties()
        properties.setProperty(user1, "password1")
        properties.setProperty(user2, "password2")
        `when`(usersMock.userMap).thenReturn(properties)

        //when:
        val actual = creator.getUserDetailsManager()

        //then:
        assert(actual.userExists(user1))
        assert(actual.userExists(user2))
    }
}
