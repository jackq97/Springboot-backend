package com.kotlinspring.service

import com.kotlinspring.dto.BookDto
import com.kotlinspring.entity.Book
import com.kotlinspring.exception.BookNotFoundException
import com.kotlinspring.repository.BookRepository
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class BookService(val courseRepository: BookRepository) {

    companion object : KLogging()

    fun addCourse(courseDto: BookDto): BookDto {
        val courseEntity = courseDto.let {
            Book(
                id = null,
                name = it.name,
                category = it.category,
            )
        }
        courseRepository.save(courseEntity)
        logger.info { "Added new course: $courseEntity" }
        return courseEntity.let {
            BookDto(id = it.id,
                name = it.name,
                category = it.category
            )
        }
    }

    fun retrieveAllCourses(): List<BookDto> {
           return courseRepository.findAll().map {
                BookDto(id = it.id, name = it.name, category = it.category    )
            }
    }

    fun updateCourse(courseId: Int, courseDto: BookDto): BookDto {
        val existingCourse = courseRepository.findById(courseId)
        return if(existingCourse.isPresent) {
            existingCourse.get().let {
                it.name = courseDto.name
                it.category = courseDto.category
                courseRepository.save(it)
                BookDto(id = it.id, name = courseDto.name, category = it.category)
            }
        } else {
            throw BookNotFoundException(
                "No course with id $courseId exists."
            )
        }
    }

    fun deleteCourse(courseId: Int) {
        val existingCourse = courseRepository.findById(courseId)
        if(existingCourse.isPresent) {
            existingCourse.get().let {
                courseRepository.deleteById(courseId)
            }
        } else {
            throw BookNotFoundException(
                "No course with id $courseId exists."
            )
                }
    }

}
