package com.thinkresearch.chatbot.channel.req

data class ReqInitialize(
    val id: String,
    val origin: String,
) : Req() {
    override fun map(): Map<String, Any> {
        return mapOf(
            "origin" to origin,
            "app" to id,
        )
    }
}