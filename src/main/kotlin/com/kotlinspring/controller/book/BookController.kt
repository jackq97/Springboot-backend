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
@RequestMapping("/v1/courses")
class BookController(val courseService: BookService) {

    @CrossOrigin
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addCourse(@RequestBody courseDto: BookDto): BookDto {
        return courseService.addCourse(courseDto)
    }

    @CrossOrigin
    @GetMapping
    fun retrieveAllCourses(): List<BookDto> = courseService.retrieveAllCourses()

    @CrossOrigin
    @PutMapping("/{course_id}")
    fun updateCourse(@PathVariable("course_id") courseId: Int, @RequestBody courseDto: BookDto): BookDto =
        courseService.updateCourse(courseId, courseDto)

    @CrossOrigin
    @DeleteMapping("/{course_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCourse(@PathVariable("course_id") courseId: Int){
        courseService.deleteCourse(courseId)
    }

}