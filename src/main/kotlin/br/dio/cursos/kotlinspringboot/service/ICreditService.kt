package br.dio.cursos.kotlinspringboot.service

import br.dio.cursos.kotlinspringboot.model.entity.Credit
import java.util.*

interface ICreditService {
    fun save(credit: Credit): Credit
    fun findAllByCustomer(customerId: Long): List<Credit>
    fun findByCreditCode(customerId: Long, creditCode: UUID): Credit
}