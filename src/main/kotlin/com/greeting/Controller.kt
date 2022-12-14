package com.greeting

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Post
import java.io.File
import java.time.LocalDateTime


@Controller("/")
class Controller {

    private val salutation = when (LocalDateTime.now().hour) {
        in 3..11 -> "Good morning"
        in 12..19 -> "Good afternoon"
        in 20..24 -> "Good evening"
        in 0 .. 2 -> "Good evening"
        else -> "Good day"
    }

    @Get("/")
    @Produces(MediaType.TEXT_JSON)
    fun index(): String {
        return File("./src/main/resources/companies.json").readText()
    }

    @Get("/property/{property}")
    fun indexII(property: String): Company {
        return ConvertService().companyFromJson(property.toInt())
    }

    @Get("property/{property}/template/{template}/guest/{guest}")
    fun indexIII(property: String, template: String, guest: String): String {

        val companyObject = ConvertService().companyFromJson(property.toInt())
        val templateObject = ConvertService().templateFromJson(template.toInt())
        val guestObject = ConvertService().guestFromJson(guest.toInt())

        val messageVariables = mapOf(
            "{salutation}" to salutation,
            "{firstName}" to guestObject.firstName,
            "{lastName}" to guestObject.lastName,
            "{roomNumber}" to guestObject.reservation.roomNumber,
            "{company}" to companyObject.company,
            "{city}" to companyObject.city,
        )

        var returnMessage = templateObject.message
        for (item in messageVariables) {
            returnMessage = returnMessage.replace(item.key, item.value.toString())
        }

        return returnMessage
    }


    @Post("/customTemplate")
    fun customTemplate(@Body body: CustomTemplate): String {

        val companyObject = ConvertService().companyFromJson(body.companyId)
        val guestObject = ConvertService().guestFromJson(body.guestId)
        val returnMessage = MessageService().createMessageFromTemplate(salutation, guestObject, companyObject, body)

        return returnMessage
    }

}