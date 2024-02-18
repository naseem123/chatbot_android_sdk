package com.thinkresearch.chatbot.channel.rsp

data class RspError(
    val message: String?,
    val stackTrace: String?,
    override val requestId: String?,
) : Rsp {
    companion object {
        fun from(map: Map<String, Any?>): RspError {
            return RspError(
                map["message"] as String?,
                map["stackTrace"] as String?,
                map["requestId"] as String?
            )
        }
    }

    override fun toString(): String {
        return "RspError(message=$message, stackTrace=$stackTrace, requestId=$requestId)"
    }
}