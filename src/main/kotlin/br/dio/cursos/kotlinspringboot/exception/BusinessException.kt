package br.dio.cursos.kotlinspringboot.exception

data class BusinessException(override var message: String): RuntimeException (message)