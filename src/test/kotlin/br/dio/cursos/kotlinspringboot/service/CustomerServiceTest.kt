package br.dio.cursos.kotlinspringboot.service

import br.dio.cursos.kotlinspringboot.exception.BusinessException
import br.dio.cursos.kotlinspringboot.model.entity.Address
import br.dio.cursos.kotlinspringboot.model.entity.Customer
import br.dio.cursos.kotlinspringboot.model.repository.CustomerRepository
import br.dio.cursos.kotlinspringboot.service.impl.CustomerService
import br.dio.cursos.kotlinspringboot.utils.DataBuilder
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.assertj.core.api.Assert
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.util.*
import kotlin.random.Random

@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class CustomerServiceTest {

    @MockK lateinit var repository: CustomerRepository
    @InjectMockKs lateinit var service: CustomerService

    @Test
    fun shouldCreateCustomer() {
        val fakeCustomer = DataBuilder.buildCustomer()

        // Equivalente ao when do Mockito:
        every { repository.save(any()) } returns fakeCustomer

        val savedCustomer = service.save(fakeCustomer)

        Assertions.assertThat(savedCustomer).isSameAs(fakeCustomer)
        verify(exactly = 1) { repository.save(any()) }
    }

    @Test
    fun shouldFindCustomer() {
        val fakeId = Random.nextLong()
        val fakeCustomer = DataBuilder.buildCustomer()

        every { repository.findById(any()) } returns Optional.of(fakeCustomer)

        val actual = service.findById(fakeId)

        Assertions.assertThat(actual).isExactlyInstanceOf(Customer::class.java)
        Assertions.assertThat(actual).isSameAs(fakeCustomer)
        verify(exactly = 1) { repository.findById(any()) }
    }

    @Test
    fun shouldNotFindCustomerAndThrowABusinessException() {
        val fakeId = Random.nextLong()

        every { repository.findById(any()) } returns Optional.empty()

        Assertions.assertThatExceptionOfType(BusinessException::class.java)
            .isThrownBy { service.findById(fakeId) }
            .withMessage("Id ${fakeId} not found")
    }

    @Test
    fun shouldDeleteCustomer() {
        val fakeId = Random.nextLong()
        val fakeCustomer = DataBuilder.buildCustomer()

        every { repository.findById(any()) } returns Optional.of(fakeCustomer)
        every { repository.delete(any()) } just runs

        service.delete(fakeId)

        verify(exactly = 1) { repository.findById(any()) }
        verify(exactly = 1) { repository.delete(any()) }
    }

    @Test
    fun shouldNotDeleteCustomerAndThrowABusinessException() {
        val fakeId = Random.nextLong()

        every { repository.findById(any()) } returns Optional.empty()
        every { repository.delete(any()) } just runs

        Assertions.assertThatExceptionOfType(BusinessException::class.java)
            .isThrownBy { service.delete(fakeId) }
            .withMessage("Id ${fakeId} not found")

        verify(exactly = 0) { repository.delete(any()) }
    }
}