package br.dio.cursos.kotlinspringboot.model.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.br.CPF
import java.math.BigDecimal

data class CustomerDataDTO(
    @field:NotEmpty(message = "Customer must have first name!") val firstName: String,
    @field:NotEmpty(message = "Customer must have last name!") val lastName: String,
    @field:CPF(message = "Invalid CPF!") val cpf: String,
    @field:NotNull(message = "Customer must have income!") val income: BigDecimal,
    @field:Email(message = "Invalid email!") val email: String,
    @field:NotEmpty(message = "Customer must have password!") val password: String,
    @field:NotEmpty(message = "Customer must have address zip code!") val zipCode: String,
    @field:NotEmpty(message = "Customer must have address street!") val street: String
)
