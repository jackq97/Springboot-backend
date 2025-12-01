package com.kotlinspring.service

import com.kotlinspring.dto.CourseDto
import com.kotlinspring.entity.Course
import com.kotlinspring.exception.CourseNotFoundException
import com.kotlinspring.repository.CourseRepository
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class CourseService(val courseRepository: CourseRepository) {

    companion object : KLogging()

    fun addCourse(courseDto: CourseDto): CourseDto {
        val courseEntity = courseDto.let {
            Course(
                id = null,
                name = it.name,
                category = it.category,
            )
        }
        courseRepository.save(courseEntity)
        logger.info { "Added new course: $courseEntity" }
        return courseEntity.let {
            CourseDto(id = it.id,
                name = it.name,
                category = it.category
            )
        }
    }

    fun retrieveAllCourses(): List<CourseDto> {
           return courseRepository.findAll().map {
                CourseDto(id = it.id, name = it.name, category = it.category    )
            }
    }

    fun updateCourse(courseId: Int, courseDto: CourseDto): CourseDto {
        val existingCourse = courseRepository.findById(courseId)
        return if(existingCourse.isPresent) {
            existingCourse.get().let {
                it.name = courseDto.name
                it.category = courseDto.category
                courseRepository.save(it)
                CourseDto(id = it.id, name = courseDto.name, category = it.category)
            }
        } else {
            throw CourseNotFoundException(
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
            throw CourseNotFoundException(
                "No course with id $courseId exists."
            )
                }
    }

}
