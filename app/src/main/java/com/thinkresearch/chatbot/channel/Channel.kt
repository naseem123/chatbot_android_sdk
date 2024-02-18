package com.thinkresearch.chatbot.channel

import android.content.Context
import com.thinkresearch.chatbot.channel.req.Req
import com.thinkresearch.chatbot.channel.req.ReqInitialize
import com.thinkresearch.chatbot.channel.rsp.Rsp
import com.thinkresearch.chatbot.channel.rsp.RspInitialize
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred

class Channel {
    private var handler: ChannelHandler? = null

    fun initialize(id: String, origin: String, context: Context): Deferred<RspInitialize> {
        handler = ChannelHandler(context)
        return request(
            DefaultMethod.INITIALIZE,
            ReqInitialize(id, origin,)
        ) { args -> RspInitialize.from(args) }
    }

    fun <S : Req, D : Rsp> request(
        method: ChannelMethod,
        request: S,
        toResponse: (Map<String, Any?>) -> D
    ): CompletableDeferred<D> {
        if (handler == null)
            throw IllegalStateException("Channel not initialized. Call .initialize(...)")
        return handler!!.invokeMethod(
            method,
            request
        ) { call -> toResponse(call.arguments as Map<String, Any?>) }
    }
}