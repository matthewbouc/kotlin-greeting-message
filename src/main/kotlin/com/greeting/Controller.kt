package com.greeting

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File
import java.time.LocalDateTime


@Controller("/")
class Controller {

    private val mapper = jacksonObjectMapper()

    @Get("/")
    @Produces(MediaType.TEXT_JSON)
    fun index(): String {
        return File("./src/main/resources/companies.json").readText()

    }

    @Get("/property/{property}")
    fun indexII(property: String): Company {

        val companyString: String = File("./src/main/resources/companies.json").readText()
        val companyList: List<Company> = mapper.readValue(companyString)

        var returnObject = Company(0, "null", "null", "null")
        for (item in companyList) {
            if (item.id == property.toInt()) {
                returnObject = item
            }
        }


        return returnObject
    }

    @Get("property/{property}/template/{template}/guest/{guest}")
    fun indexIII(property: String, template: String, guest: String): String {

        val companyString: String = File("./src/main/resources/companies.json").readText()
        val companyList: List<Company> = mapper.readValue(companyString)

        var companyObject = Company(0, "null", "null", "null")
        for (item in companyList) {
            if (item.id == property.toInt()) {
                companyObject = item
            }
        }

        val salutation = when (LocalDateTime.now().hour) {
            in 3..11 -> "Good morning"
            in 12..19 -> "Good afternoon"
            in 20..2 -> "Good evening"
            else -> "Good day"
        }

        val templateString = File("./src/main/resources/greetingTemplates.json").readText()
        val templateList: List<Template> = mapper.readValue(templateString)

        var templateObject = Template(0, "null")
        for (item in templateList) {
            if (item.id == template.toInt()) {
                templateObject = item
            }
        }


        val guestString = File("./src/main/resources/guests.json").readText()
        val guestList: List<Guest> = mapper.readValue(guestString)

        val reservation = Reservation(0, 0, 0)
        var guestObject = Guest(0, "null", "null", reservation)

        for (item in guestList) {
            if (item.id == guest.toInt()) {
                guestObject = item
            }
        }

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



}