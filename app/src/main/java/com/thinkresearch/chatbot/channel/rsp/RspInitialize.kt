/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.thinkresearch.chatbot.channel.rsp

data class RspInitialize(
    val id: String?,
    val address: String?,
    override val requestId: String?,
) : Rsp {
    companion object {
        fun from(map: Map<String, Any?>): RspInitialize {
            return RspInitialize(
                map["id"] as String?,
                map["address"] as String?,
                map["requestId"] as String?
            )
        }
    }
}
