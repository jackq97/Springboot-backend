package com.kotlinspring.service

import com.kotlinspring.dto.BookDto
import com.kotlinspring.entity.Book
import com.kotlinspring.exception.BookNotFoundException
import com.kotlinspring.repository.BookRepository
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class BookService(val bookRepository: BookRepository) {

    companion object : KLogging()

    fun addBook(courseDto: BookDto): BookDto {
        val courseEntity = courseDto.let {
            Book(
                id = null,
                name = it.name,
                category = it.category,
            )
        }
        bookRepository.save(courseEntity)
        logger.info { "Added new course: $courseEntity" }
        return courseEntity.let {
            BookDto(id = it.id,
                name = it.name,
                category = it.category
            )
        }
    }

    fun retrieveAllBooks(): List<BookDto> {
           return bookRepository.findAll().map {
                BookDto(id = it.id, name = it.name, category = it.category    )
            }
    }

    fun updateBook(bookId: Int, courseDto: BookDto): BookDto {
        val existingCourse = bookRepository.findById(bookId)
        return if(existingCourse.isPresent) {
            existingCourse.get().let {
                it.name = courseDto.name
                it.category = courseDto.category
                bookRepository.save(it)
                BookDto(id = it.id, name = courseDto.name, category = it.category)
            }
        } else {
            throw BookNotFoundException(
                "No course with id $bookId exists."
            )
        }
    }

    fun deleteBook(bookId: Int) {
        val existingCourse = bookRepository.findById(bookId)
        if(existingCourse.isPresent) {
            existingCourse.get().let {
                bookRepository.deleteById(bookId)
            }
        } else {
            throw BookNotFoundException(
                "No course with id $bookId exists."
            )
                }
    }

}
