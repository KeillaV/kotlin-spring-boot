package br.dio.cursos.kotlinspringboot.model.dto

import java.math.BigDecimal

data class CustomerDataDTO(
    val firstName: String,
    val lastName: String,
    val cpf: String,
    val income: BigDecimal,
    val email: String,
    val password: String,
    val zipCode: String,
    val street: String
)
