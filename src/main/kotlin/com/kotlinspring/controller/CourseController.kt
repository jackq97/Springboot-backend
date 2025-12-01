package com.kotlinspring.controller

import com.kotlinspring.dto.CourseDto
import com.kotlinspring.service.CourseService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/courses")
class CourseController(val courseService: CourseService) {

    @CrossOrigin
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addCourse(@RequestBody courseDto: CourseDto): CourseDto{
        return courseService.addCourse(courseDto)
    }

    @CrossOrigin
    @GetMapping
    fun retrieveAllCourses(): List<CourseDto> = courseService.retrieveAllCourses()

    @CrossOrigin
    @PutMapping("/{course_id}")
    fun updateCourse(@PathVariable("course_id") courseId: Int, @RequestBody courseDto: CourseDto): CourseDto =
        courseService.updateCourse(courseId, courseDto)

    @CrossOrigin
    @DeleteMapping("/{course_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCourse(@PathVariable("course_id") courseId: Int){
        courseService.deleteCourse(courseId)
    }

}