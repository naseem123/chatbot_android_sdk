package com.thinkresearch.chatbot.channel.rsp

data class RspDefault(override val requestId: String?) : Rsp {
    companion object {
        fun from(map: Map<String, Any?>): RspDefault {
            return RspDefault(
                map["requestId"] as String?
            )
        }
    }
}