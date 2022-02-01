package io.github.redstoneparadox.buildscript.task

import java.util.function.Consumer

fun interface Task<C: TaskContext> {
    fun accept(consumer: Consumer<TaskContext>)
}