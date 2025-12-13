package com.kotlinspring.controller.util

import com.kotlinspring.entity.Book


fun courseEntityList() = listOf(
    Book(
        null,
        "Build RestFul APis using SpringBoot and Kotlin", "Development"
    ),
    Book(null,
        "Build Reactive Microservices using Spring WebFlux/SpringBoot", "Development"
        ,
    ),
    Book(null,
        "Wiremock for Java Developers", "Development" ,
    )
)