package br.dio.cursos.kotlinspringboot.service.impl

import br.dio.cursos.kotlinspringboot.model.dto.CustomerDataDTO
import br.dio.cursos.kotlinspringboot.model.dto.CustomerUpdateDTO
import br.dio.cursos.kotlinspringboot.model.dto.CustomerViewDTO
import br.dio.cursos.kotlinspringboot.model.entity.Address
import br.dio.cursos.kotlinspringboot.model.entity.Customer
import org.springframework.stereotype.Service

@Service
class CustomerConverterService {
    fun dtoToEntity(dto: CustomerDataDTO): Customer = Customer(
        firstName = dto.firstName,
        lastName = dto.lastName,
        cpf = dto.cpf,
        income = dto.income,
        email = dto.email,
        password = dto.password,
        address = Address(dto.zipCode, dto.street)
        )

    fun dtoToEntity(dto: CustomerUpdateDTO, entity: Customer): Customer {
        entity.firstName = dto.firstName
        entity.lastName = dto.lastName
        entity.income = dto.income
        entity.address.zipCode = dto.zipCode
        entity.address.street = dto.street
        return entity
    }
    fun entityToDto(entity: Customer) = CustomerViewDTO(
        id = entity.id,
        firstName = entity.firstName,
        lastName = entity.lastName,
        cpf = entity.cpf,
        income = entity.income,
        email = entity.email,
        zipCode = entity.address.zipCode,
        street = entity.address.street
    )
}