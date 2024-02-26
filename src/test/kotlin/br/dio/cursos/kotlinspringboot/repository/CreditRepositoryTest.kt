package br.dio.cursos.kotlinspringboot.repository

import br.dio.cursos.kotlinspringboot.model.entity.Credit
import br.dio.cursos.kotlinspringboot.model.entity.Customer
import br.dio.cursos.kotlinspringboot.model.repository.CreditRepository
import br.dio.cursos.kotlinspringboot.utils.DataBuilder
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles
import java.util.*

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CreditRepositoryTest {
    @Autowired lateinit var repository: CreditRepository
    @Autowired lateinit var testEntityManager: TestEntityManager

    private lateinit var customer: Customer
    private lateinit var credit1: Credit
    private lateinit var credit2: Credit

    @BeforeEach fun setup() {
        customer = testEntityManager.persist(DataBuilder.buildCustomer())
        credit1 = testEntityManager.persist(DataBuilder.buildCredit("5b1b734a-e9a7-413f-b0f2-3fc65008921e", customer))
        credit2 = testEntityManager.persist(DataBuilder.buildCredit("c26ad0cf-2a56-477f-bccc-7603c3a67d8c", customer))
    }

    @Test
    fun shouldFindCreditByCode() {
        var creditCode1 = UUID.fromString("5b1b734a-e9a7-413f-b0f2-3fc65008921e")
        var creditCode2 = UUID.fromString("c26ad0cf-2a56-477f-bccc-7603c3a67d8c")

        val fakeCredit1 = repository.findByCreditCode(creditCode1)
        val fakeCredit2 = repository.findByCreditCode(creditCode2)

        Assertions.assertThat(fakeCredit1).isNotNull
        Assertions.assertThat(fakeCredit2).isNotNull

        Assertions.assertThat(fakeCredit1).isSameAs(credit1)
        Assertions.assertThat(fakeCredit2).isSameAs(credit2)
    }

    @Test
    fun shouldFindAllCreditsByCustomerId() {
        val creditList = repository.findAllByCustomer(customer?.id!!)

        Assertions.assertThat(creditList).isNotEmpty
        Assertions.assertThat(creditList).contains(credit1, credit2)
    }
}
