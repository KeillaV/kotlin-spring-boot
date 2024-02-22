package br.dio.cursos.kotlinspringboot.model.repository

import br.dio.cursos.kotlinspringboot.model.entity.Credit
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CreditRepository: JpaRepository<Credit, Long>