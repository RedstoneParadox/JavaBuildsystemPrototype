package io.github.redstoneparadox.buildscript.task

import java.util.function.Consumer

fun interface Task<TC: TaskContext> {
    fun accept(consumer: Consumer<TC>)
}