package br.dio.cursos.kotlinspringboot.service.impl

import br.dio.cursos.kotlinspringboot.exception.BusinessException
import br.dio.cursos.kotlinspringboot.model.entity.Credit
import br.dio.cursos.kotlinspringboot.model.repository.CreditRepository
import br.dio.cursos.kotlinspringboot.service.ICreditService
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*

@Service
class CreditService (
    private val creditRepository: CreditRepository,
    private val customerService: CustomerService
): ICreditService {

    override fun save(credit: Credit): Credit {
        if (credit.dayFirstInstallment.isAfter(LocalDate.now().plusMonths(3))) {
            throw BusinessException("The date for payment of the first installment should be no later than three months from now!")
        }

        credit.apply {
            customer = customerService.findById(credit.customer?.id!!)
        }
        return this.creditRepository.save(credit)
    }

    override fun findAllByCustomer(customerId: Long): List<Credit> = this.creditRepository.findAllByCustomer(customerId)

    override fun findByCreditCode(customerId: Long, creditCode: UUID): Credit {
        val credit: Credit = this.creditRepository.findByCreditCode(creditCode)
            ?: throw RuntimeException("Credit code $creditCode not found!")

        return if (credit.customer?.id == customerId) credit else throw BusinessException("Invalid customer, contact administrator")
    }
}