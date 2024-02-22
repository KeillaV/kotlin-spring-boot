package br.dio.cursos.kotlinspringboot.service.impl

import br.dio.cursos.kotlinspringboot.model.entity.Customer
import br.dio.cursos.kotlinspringboot.model.repository.CustomerRepository
import br.dio.cursos.kotlinspringboot.service.ICustomerService
import org.springframework.stereotype.Service
import java.lang.RuntimeException

@Service
class CustomerService (
    private val repository: CustomerRepository
): ICustomerService {

    override fun save(customer: Customer): Customer = this.repository.save(customer)

    override fun findById(id: Long): Customer = this.repository.findById(id).orElseThrow {
            throw RuntimeException("Id $id not found")
        }

    override fun delete(id: Long) = this.repository.deleteById(id)
}