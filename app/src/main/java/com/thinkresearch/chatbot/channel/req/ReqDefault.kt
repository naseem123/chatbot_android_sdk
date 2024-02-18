package com.thinkresearch.chatbot.channel.req

class ReqDefault : Req() {
    override fun map(): Map<String, Any> {
        return mapOf("requestId" to requestId)
    }
}