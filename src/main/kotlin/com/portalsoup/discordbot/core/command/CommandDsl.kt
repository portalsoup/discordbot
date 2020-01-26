package com.portalsoup.discordbot.core.command

import net.dv8tion.jda.api.events.Event

@DslMarker annotation class CommandDsl

/*
 * Command
 */
fun <E : Event> command(lambda: CommandBuilder<E>.() -> Unit) = CommandBuilder<E>().apply(lambda).build()

data class Command<E : Event>(
    val job: Job<E>,
    val preconditions: List<(E) -> Boolean>,
    val description: String,
    val name: String
)

@CommandDsl
open class CommandBuilder<E : Event> : AbstractCommandBuilder<E>() {
    internal var job: Job<E> = Job(listOf())
    internal var preconditions = mutableListOf<(E) -> Boolean>()
    internal var description = ""
    internal var name = ""

    fun job(lambda: JobBuilder<E>.() -> Unit) {
        job = JobBuilder<E>().apply(lambda).build()
    }

    fun preconditions(lambda: PreconditionListBuilder<E>.() -> Unit) {
        preconditions.addAll(
            PreconditionListBuilder<E>().apply(lambda).build()
        )
    }

    override fun description(lambda: () -> String) {
        description = lambda()
    }

    override fun name(lambda: () -> String) {
        name = lambda()
    }

    override fun build(): Command<E> =
        Command(name = name, description = description, job = job, preconditions = preconditions)
}

/*
 * Precondition
 */
@CommandDsl
open class PreconditionListBuilder<E : Event> {
    internal val preconditions = mutableListOf<(E) -> Boolean>()

    fun predicate(lambda: (E) -> Boolean) {
        preconditions.add(lambda)
    }

    fun build() = preconditions
}

/*
 * Job
 */
data class Job<in E : Event>(val run: List<(E) -> Unit>)

@CommandDsl
open class JobBuilder<E : Event> {
    internal var run: MutableList<(E) -> Unit> = mutableListOf()

    fun addRunner(lambda: (E) -> Unit) {
        this.run.add(lambda)
    }

    fun build() = Job(run)
}