/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.thinkresearch.chatbot.channel

enum class DefaultMethod(private val value: String) : ChannelMethod {
    INITIALIZE("initialize");

    override fun value(): String {
        return this.value
    }
}