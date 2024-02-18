/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */
package com.thinkresearch.chatbot.channel

import android.content.Context
import android.util.Log
import androidx.annotation.NonNull
import com.thinkresearch.chatbot.FLUTTER_ENGINE_ID
import com.thinkresearch.chatbot.channel.req.Req
import com.thinkresearch.chatbot.channel.rsp.Rsp
import com.thinkresearch.chatbot.channel.rsp.RspError
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.embedding.engine.loader.FlutterLoader
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugins.GeneratedPluginRegistrant
import kotlinx.coroutines.CompletableDeferred

class ChannelHandler(context: Context) : FlutterPlugin, MethodCallHandler {

    private lateinit var channel: MethodChannel
    private var completables: MutableMap<String, ((MethodCall) -> Unit)> =
        mutableMapOf()

    init {
        val loader = FlutterLoader()
        loader.startInitialization(context)
        loader.ensureInitializationComplete(context, null)
        val flutterEngine = FlutterEngine(context)
        flutterEngine.dartExecutor.executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault())
        flutterEngine.plugins.add(this)
        GeneratedPluginRegistrant.registerWith(flutterEngine)
        FlutterEngineCache
            .getInstance()
            .put(FLUTTER_ENGINE_ID, flutterEngine)
    }

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "chatbot_channel")
        channel.setMethodCallHandler(this)
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        Log.d("TIKI", "Response: ${call.method} - ${call.arguments}")
        val requestId = call.argument<String>("requestId")!!
        completables[requestId]?.invoke(call)
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    fun <S : Req, D : Rsp> invokeMethod(
        method: ChannelMethod,
        request: S,
        toResponse: (MethodCall) -> D
    ): CompletableDeferred<D> {
        Log.d("TIKI", "Request: ${method.value()} - ${request.map()}")
        val deferred = CompletableDeferred<D>()
        channel.invokeMethod(method.value(), request.map())
        completables[request.requestId] = { call: MethodCall ->
            when (call.method) {
                "success" -> deferred.complete(toResponse(call))
                "error" -> deferred.completeExceptionally(
                    Error(
                        RspError.from(call.arguments as Map<String, Any?>).toString()
                    )
                )
            }
        }
        return deferred
    }
}
