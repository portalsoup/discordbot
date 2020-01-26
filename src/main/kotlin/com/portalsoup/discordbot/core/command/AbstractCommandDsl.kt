package com.portalsoup.discordbot.core.command

import net.dv8tion.jda.api.events.Event

abstract class AbstractCommandBuilder<E : Event> {

    abstract fun description(lambda: () -> String)

    abstract fun name(lambda: () -> String)

    abstract fun build(): Command<E>
}