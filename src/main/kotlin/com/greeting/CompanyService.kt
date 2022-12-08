package com.greeting

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File

class ConvertService {

    private val mapper = jacksonObjectMapper()

    fun companyFromJson (property: Int): Company {
        val companyString: String = File("./src/main/resources/companies.json").readText()
        val companyList: List<Company> = mapper.readValue(companyString)

        var companyObject = Company(0, "null", "null", "null")
        for (item in companyList) {
            if (item.id == property) {
                companyObject = item
            }
        }

        return companyObject
    }


    fun templateFromJson (templateId: Int): Template {
        val templateString = File("./src/main/resources/greetingTemplates.json").readText()
        val templateList: List<Template> = mapper.readValue(templateString)

        var templateObject = Template(0, "null")
        for (item in templateList) {
            if (item.id == templateId) {
                templateObject = item
            }
        }

        return templateObject
    }


    fun guestFromJson (guestId: Int): Guest {
        val guestString = File("./src/main/resources/guests.json").readText()
        val guestList: List<Guest> = mapper.readValue(guestString)

        val reservation = Reservation(0, 0, 0)
        var guestObject = Guest(0, "null", "null", reservation)

        for (item in guestList) {
            if (item.id == guestId) {
                guestObject = item
            }
        }

        return guestObject
    }
}
