package com.thinkresearch.chatbot.channel.req

import java.util.UUID

abstract class Req {
    val requestId: String = UUID.randomUUID().toString()
    abstract fun map(): Map<String, Any?>
}