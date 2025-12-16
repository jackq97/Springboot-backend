package com.kotlinspring.controller.book

import com.kotlinspring.dto.BookDto
import com.kotlinspring.service.BookService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/books")
class BookController(val bookService: BookService) {

    @CrossOrigin
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addBook(@RequestBody bookDto: BookDto): BookDto {
        return bookService.addBook(bookDto)
    }

    @CrossOrigin
    @GetMapping
    fun retrieveAllBooks(): List<BookDto> = bookService.retrieveAllBooks()

    @CrossOrigin
    @PutMapping("/{book_id}")
    fun updateBook(@PathVariable("book_id") bookId: Int, @RequestBody bookDto: BookDto): BookDto =
        bookService.updateBook(bookId, bookDto)

    @CrossOrigin
    @DeleteMapping("/{book_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCourse(@PathVariable("book_id") bookId: Int){
        bookService.deleteBook(bookId)
    }

}