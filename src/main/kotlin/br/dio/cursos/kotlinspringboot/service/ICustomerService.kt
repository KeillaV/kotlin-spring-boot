package br.dio.cursos.kotlinspringboot.service

import br.dio.cursos.kotlinspringboot.model.entity.Customer

interface ICustomerService {
    fun save (customer: Customer): Customer
    fun findById(id: Long): Customer
    fun delete(id: Long)
}