package br.dio.cursos.kotlinspringboot.utils

import br.dio.cursos.kotlinspringboot.model.dto.CustomerDataDTO
import br.dio.cursos.kotlinspringboot.model.dto.CustomerUpdateDTO
import br.dio.cursos.kotlinspringboot.model.entity.Address
import br.dio.cursos.kotlinspringboot.model.entity.Credit
import br.dio.cursos.kotlinspringboot.model.entity.Customer
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

class DataBuilder {
    companion object Builder {
        fun buildCustomer() = Customer(
            firstName = "Keilla",
            lastName = "Bezerra",
            cpf = "86705758090",
            email = "keilla@gmail.com",
            password = "123456",
            address = Address(
                zipCode = "00000-0000",
                street = "Rua das laranjas"
            ), income = BigDecimal(2000.0)
        )

        fun buildCustomerDataDto(firstName: String = "Keilla") = CustomerDataDTO(
            firstName = firstName,
            lastName = "Bezerra",
            cpf = "86705758090",
            email = "keilla@gmail.com",
            password = "123456",
            zipCode = "00000-0000",
            street = "Rua das laranjas",
            income = BigDecimal(2000.0)
        )

        fun buildCustomerUpdateDto() = CustomerUpdateDTO(
            firstName = "Keilla",
            lastName = "Bezerra",
            zipCode = "11111-1111",
            street = "Rua das maçãs",
            income = BigDecimal(5000.0)
        )


        fun buildCredit(creditCode: String, customer: Customer) = Credit(
            creditCode = UUID.fromString(creditCode),
            creditValue = BigDecimal(500.0),
            dayFirstInstallment = LocalDate.now().plusDays(5),
            numberOfInstallments = 5,
            customer = customer
        )
    }
}