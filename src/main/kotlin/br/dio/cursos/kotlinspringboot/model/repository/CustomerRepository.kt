package br.dio.cursos.kotlinspringboot.model.repository

import br.dio.cursos.kotlinspringboot.model.entity.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository: JpaRepository<Customer, Long>