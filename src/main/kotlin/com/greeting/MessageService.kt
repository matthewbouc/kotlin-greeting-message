package com.greeting

class MessageService {

    fun createMessageFromTemplate (salutation: String, guestObject: Guest, companyObject: Company, body: CustomTemplate): String{

        val messageVariables = mapOf(
            "{salutation}" to salutation,
            "{firstName}" to guestObject.firstName,
            "{lastName}" to guestObject.lastName,
            "{roomNumber}" to guestObject.reservation.roomNumber,
            "{company}" to companyObject.company,
            "{city}" to companyObject.city,
        )

        var returnMessage = body.message
        for (item in messageVariables) {
            returnMessage = returnMessage.replace(item.key, item.value.toString())
        }

        return returnMessage
    }


}