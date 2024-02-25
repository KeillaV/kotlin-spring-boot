package br.dio.cursos.kotlinspringboot.controller

import br.dio.cursos.kotlinspringboot.model.dto.CreditDataDTO
import br.dio.cursos.kotlinspringboot.model.dto.CreditViewDTO
import br.dio.cursos.kotlinspringboot.service.impl.CreditConverterService
import br.dio.cursos.kotlinspringboot.service.impl.CreditService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/credits")
class CreditController (
    private val service: CreditService,
    private val converterService: CreditConverterService
){

    @PostMapping
    fun save(@RequestBody @Valid dto: CreditDataDTO): ResponseEntity<CreditViewDTO> {
        var entity = service.save(converterService.dtoToEntity(dto))
        return ResponseEntity(converterService.entityToDto(entity), HttpStatus.CREATED)
    }

    @GetMapping
    fun getAllByCustomerId(@RequestParam(value = "customerId") id: Long): ResponseEntity<List<CreditViewDTO>> {
        return ResponseEntity.ok(converterService.entityToDto(service.findAllByCustomer(id)))
    }

    @GetMapping("/customer/{id}")
    fun getByCreditCode(@PathVariable id: Long, @RequestParam(value = "creditCode") creditCode: UUID): ResponseEntity<CreditViewDTO> {
        return ResponseEntity.ok(converterService.entityToDto(service.findByCreditCode(id, creditCode)))
    }
}