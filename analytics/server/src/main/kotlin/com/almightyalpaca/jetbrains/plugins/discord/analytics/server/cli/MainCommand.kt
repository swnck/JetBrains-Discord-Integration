/*
 * Copyright 2017-2020 Aljoscha Grebe
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.almightyalpaca.jetbrains.plugins.discord.analytics.server.cli

import com.almightyalpaca.jetbrains.plugins.discord.analytics.server.hoplite.FixedSystemPropertyPreprocessor
import com.almightyalpaca.jetbrains.plugins.discord.analytics.server.routes.runServer
import com.almightyalpaca.jetbrains.plugins.discord.analytics.server.services.Configuration
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.path
import com.sksamuel.hoplite.ConfigLoader
import io.ktor.util.*
import java.nio.file.Path
import java.nio.file.Paths

class MainCommand : CliktCommand() {
    private val configDefault = Paths.get("application.conf")
    private val config: Path by option(help = "Config file").path(mustExist = true, canBeDir = false, mustBeReadable = true).default(configDefault)

    @KtorExperimentalAPI
    override fun run() {
        ConfigLoader()
        val configuration =
            ConfigLoader.Builder()
                .addPreprocessor(FixedSystemPropertyPreprocessor)
                .build()
                .loadConfigOrThrow<Configuration>(config)

        runServer(configuration)
    }
}
