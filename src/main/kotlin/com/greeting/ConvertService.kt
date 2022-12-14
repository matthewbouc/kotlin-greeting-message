package com.greeting

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File

class ConvertService {

    private val mapper = jacksonObjectMapper()

    fun companyFromJson (property: Int): Company {
        val companyString: String = File("./src/main/resources/companies.json").readText()
        val companyList: List<Company> = mapper.readValue(companyString)

        for (item in companyList) {
            if (item.id == property) {
                return item
            }
        }
        throw Exception("company not found")
    }

    fun templateFromJson (templateId: Int): Template {
        val templateString = File("./src/main/resources/greetingTemplates.json").readText()
        val templateList: List<Template> = mapper.readValue(templateString)

        for (item in templateList) {
            if (item.id == templateId) {
                return item
            }
        }
        throw Exception("template not found")
    }

    fun guestFromJson (guestId: Int): Guest {
        val guestString = File("./src/main/resources/guests.json").readText()
        val guestList: List<Guest> = mapper.readValue(guestString)

        for (item in guestList) {
            if (item.id == guestId) {
                return item
            }
        }
        throw Exception("guest not found")
    }
}
