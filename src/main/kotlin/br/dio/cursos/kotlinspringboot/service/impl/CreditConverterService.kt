package br.dio.cursos.kotlinspringboot.service.impl

import br.dio.cursos.kotlinspringboot.model.dto.CreditDataDTO
import br.dio.cursos.kotlinspringboot.model.dto.CreditViewDTO
import br.dio.cursos.kotlinspringboot.model.entity.Credit
import br.dio.cursos.kotlinspringboot.model.entity.Customer
import org.springframework.stereotype.Service

@Service
class CreditConverterService {

    fun dtoToEntity(dto: CreditDataDTO): Credit = Credit(
        creditValue = dto.creditValue,
        dayFirstInstallment = dto.dayFirstInstallment,
        numberOfInstallments = dto.numberOfInstallments,
        customer = Customer(id = dto.customerId)
    )

    fun entityToDto(entity: Credit): CreditViewDTO = CreditViewDTO(
        creditCode = entity.creditCode,
        creditValue = entity.creditValue,
        dayFirstInstallment = entity.dayFirstInstallment,
        numberOfInstallments = entity.numberOfInstallments,
        status = entity.status,
        emailCustomer = entity.customer?.email!!,
        incomeCustomer = entity.customer?.income!!
    )

    fun entityToDto(entities: List<Credit>): List<CreditViewDTO> = entities.map { entityToDto(it) }
}