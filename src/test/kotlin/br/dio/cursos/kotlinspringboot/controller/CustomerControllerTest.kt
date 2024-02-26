package br.dio.cursos.kotlinspringboot.controller

import br.dio.cursos.kotlinspringboot.model.repository.CustomerRepository
import br.dio.cursos.kotlinspringboot.utils.DataBuilder
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.*

@SpringBootTest
@ActiveProfiles("teste")
@AutoConfigureMockMvc
@ContextConfiguration
class CustomerControllerTest {
    @Autowired private lateinit var repository: CustomerRepository
    @Autowired private lateinit var mockMvc: MockMvc
    @Autowired private lateinit var objectMapper: ObjectMapper

    companion object {
        const val URL: String = "/api/customers"
    }

    @Test
    fun shouldCreateACostumerAndReturn201Status() {
        var dto = DataBuilder.buildCustomerDataDto()
        var dtoAsString = objectMapper.writeValueAsString(dto)

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(dtoAsString))
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Keilla"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Bezerra"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("86705758090"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("keilla@gmail.com"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.income").value("2000"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.zipCode").value("00000-0000"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.street").value("Rua das laranjas"))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun shouldNotSaveACustomerWithSameCPFAndReturn409Status() {
        repository.save(DataBuilder.buildCustomer())

        var dto = DataBuilder.buildCustomerDataDto()
        var dtoAsString = objectMapper.writeValueAsString(dto)

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(dtoAsString))
            .andExpect(MockMvcResultMatchers.status().isConflict)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Data Access Error"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(409))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception")
                    .value("DataIntegrityViolationException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun shouldNotSaveACustomerWithEmptyFirstNameAndReturn400Status() {
        var dto = DataBuilder.buildCustomerDataDto(firstName = "")

        var dtoAsString = objectMapper.writeValueAsString(dto)

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(dtoAsString))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Validation Error"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception")
                    .value("MethodArgumentNotValidException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun shouldFindACustomerByIdAndReturn200Status() {

        val dto = repository.save(DataBuilder.buildCustomer())

        mockMvc.perform(
            MockMvcRequestBuilders.get("$URL/${dto.id}")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Keilla"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Bezerra"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("86705758090"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("keilla@gmail.com"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.income").value("2000"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.zipCode").value("00000-0000"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.street").value("Rua das laranjas"))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun shouldNotFindCustomerWithInvalidIdAndReturn400Status() {
        val invalidId = Random().nextLong()

        mockMvc.perform(
            MockMvcRequestBuilders.get("$URL/$invalidId")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Business Error"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception")
                    .value("BusinessException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun shouldDeleteCustomerAndReturn204Status() {
        val dto = repository.save(DataBuilder.buildCustomer())

        mockMvc.perform(
            MockMvcRequestBuilders.delete("$URL/${dto.id}")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isNoContent)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun shouldNotDeleteCustomerByIdAndReturn400Status() {
        val invalidId = Random().nextLong()

        mockMvc.perform(
            MockMvcRequestBuilders.delete("$URL/${invalidId}")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Business Error"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception")
                    .value("BusinessException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun shouldUpdateCustomerAndReturn200Status() {
        val dto = repository.save(DataBuilder.buildCustomer())
        val updateDto = DataBuilder.buildCustomerUpdateDto()
        val valueAsString= objectMapper.writeValueAsString(updateDto)


        mockMvc.perform(
            MockMvcRequestBuilders.patch("$URL?customerId=${dto.id}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Keilla"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Bezerra"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("86705758090"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("keilla@gmail.com"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.income").value("5000"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.zipCode").value("11111-1111"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.street").value("Rua das maçãs"))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `should not update a customer with invalid id and return 400 status`() {
        val invalidId = Random().nextLong()
        val updateDto = DataBuilder.buildCustomerDataDto()
        val valueAsString = objectMapper.writeValueAsString(updateDto)

        mockMvc.perform(
            MockMvcRequestBuilders.patch("$URL?customerId=$invalidId")
                .contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Business Error"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception")
                    .value("BusinessException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andDo(MockMvcResultHandlers.print())
    }
    @BeforeEach fun setup() = repository.deleteAll()
    @AfterEach fun tearDown() = repository.deleteAll()
}