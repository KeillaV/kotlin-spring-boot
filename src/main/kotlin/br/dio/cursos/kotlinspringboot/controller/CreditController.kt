package br.dio.cursos.kotlinspringboot.controller

import br.dio.cursos.kotlinspringboot.model.dto.CreditDataDTO
import br.dio.cursos.kotlinspringboot.model.dto.CreditViewDTO
import br.dio.cursos.kotlinspringboot.service.impl.CreditConverterService
import br.dio.cursos.kotlinspringboot.service.impl.CreditService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
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

@Tag(name = "Credit", description = "Endpoints for customer credit management")
@RestController
@RequestMapping("/api/credits")
class CreditController (
    private val service: CreditService,
    private val converterService: CreditConverterService
){

    @Operation(
        summary = "Request credit for costumer",
        description = "Save a credit request for customer")
    @PostMapping
    fun save(@RequestBody @Valid dto: CreditDataDTO): ResponseEntity<CreditViewDTO> {
        var entity = service.save(converterService.dtoToEntity(dto))
        return ResponseEntity(converterService.entityToDto(entity), HttpStatus.CREATED)
    }

    @Operation(
        summary = "Get credits by customer id",
        description = "Retrieve all the credits requested by costumer")
    @GetMapping
    fun getAllByCustomerId(@RequestParam(value = "customerId") id: Long): ResponseEntity<List<CreditViewDTO>> {
        return ResponseEntity.ok(converterService.entityToDto(service.findAllByCustomer(id)))
    }

    @Operation(
        summary = "Get credits by code",
        description = "Retrieve all the credits requested by code and costumer id")
    @GetMapping("/customer/{id}")
    fun getByCreditCode(@PathVariable id: Long, @RequestParam(value = "creditCode") creditCode: UUID): ResponseEntity<CreditViewDTO> {
        return ResponseEntity.ok(converterService.entityToDto(service.findByCreditCode(id, creditCode)))
    }
}