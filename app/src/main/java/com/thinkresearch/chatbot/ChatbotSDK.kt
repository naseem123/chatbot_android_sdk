/*
 * Copyright (c) ThinkResearch Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.thinkresearch.chatbot
import android.content.Context
import com.thinkresearch.chatbot.channel.ChannelHandler
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async

class ChatBotSDK {
    private var handler: ChannelHandler? = null
    private var _appId: String? = null
    private var _origin: String? = null

    private
    fun initialize(
        appId: String,
        origin: String,
        context: Context,
    ): Deferred<Unit> {
        return MainScope().async {
            handler = ChannelHandler(context,appId, origin)
            }
        }

    private val isInitialized: Boolean
        get() = handler != null && _appId!=null && _origin!= null

    fun start(context: Context) {
        throwIfNotInitialized()
            handler?.startChatBot(context)
        }

    private fun throwIfNotInitialized() {
        if (!isInitialized) {
            throw IllegalStateException("Please ensure that the ChatBot SDK is properly initialized by calling ChatBotSDK.initialize().")
        }
    }
}