package com.kotlinspring.repository

import com.kotlinspring.entity.Book
import org.springframework.data.repository.CrudRepository

interface BookRepository: CrudRepository<Book, Int>
