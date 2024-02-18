package com.thinkresearch.chatbot

import android.app.Application
import com.thinkresearch.chatbot.channel.FlutterChannel
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.embedding.engine.loader.FlutterLoader

const val FLUTTER_ENGINE_ID = "flutter_engine"

class ThinkResearchChatBotApplication : Application() {
    private lateinit var flutterEngine : FlutterEngine
    private lateinit var flutterChannel: FlutterChannel

    override fun onCreate() {
        super.onCreate()

        val loader = FlutterLoader()
        loader.startInitialization(this)
        loader.ensureInitializationComplete(this, null)

        flutterEngine = FlutterEngine(this)

        flutterEngine.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault(),
            listOf("yB9BJmrcH3bM4CShtMKB5qrw","test.ca.digital-front-door.stg.gcp.trchq.com")
        )

        flutterChannel = FlutterChannel()
        flutterEngine.plugins.add(flutterChannel)

        //initialise()

        FlutterEngineCache
            .getInstance()
            .put(FLUTTER_ENGINE_ID, flutterEngine)

    }

}