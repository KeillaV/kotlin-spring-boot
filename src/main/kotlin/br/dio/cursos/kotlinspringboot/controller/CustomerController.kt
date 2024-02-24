package br.dio.cursos.kotlinspringboot.controller

import br.dio.cursos.kotlinspringboot.model.dto.CustomerDataDTO
import br.dio.cursos.kotlinspringboot.model.dto.CustomerUpdateDTO
import br.dio.cursos.kotlinspringboot.model.dto.CustomerViewDTO
import br.dio.cursos.kotlinspringboot.service.impl.CustomerConverterService
import br.dio.cursos.kotlinspringboot.service.impl.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/customers")
class CustomerController (
    private val service: CustomerService,
    private val converterService: CustomerConverterService
) {

    @PostMapping
    fun save(@RequestBody dto: CustomerDataDTO): ResponseEntity<CustomerViewDTO> {
        val entity = service.save(converterService.dtoToEntity(dto))
        return ResponseEntity(converterService.entityToDto(entity), HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long) = ResponseEntity.ok(converterService.entityToDto(service.findById(id)))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<String> {
        service.delete(id)
        return ResponseEntity("Deleted successfully", HttpStatus.NO_CONTENT)
    }

    @PatchMapping
    fun update(@RequestParam(value = "customerId") id: Long,
               @RequestBody dto: CustomerUpdateDTO):  ResponseEntity<CustomerViewDTO> {
        val entity = service.findById(id)
        val updatedEntity = converterService.dtoToEntity(dto, entity)
        return ResponseEntity.ok(converterService.entityToDto(service.save(updatedEntity)))
    }
}