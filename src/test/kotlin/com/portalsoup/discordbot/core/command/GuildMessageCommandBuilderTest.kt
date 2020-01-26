package com.portalsoup.discordbot.core.command

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

internal class GuildMessageCommandBuilderTest {
    companion object {
        class TestCommand<E : GuildMessageReceivedEvent>(command: Command<E>) : GuildMessageReceivedCommand<E>(command)
    }

    val x = TestCommand<GuildMessageReceivedEvent>(

        sendMessage {
            preconditions {
                message {
                    beginsWith { "" }
                    matches { "" }
                    equals { "" }
                }

                sender {
                    beginsWith { "" }
                    matches { "" }
                    equals { "" }
                }
            }

            job {
                addRunner {

                }
            }
        }
    )
}