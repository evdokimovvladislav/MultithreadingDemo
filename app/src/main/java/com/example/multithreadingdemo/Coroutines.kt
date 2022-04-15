package com.example.multithreadingdemo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    println("Работа async/await")
    val num1 = async(Dispatchers.IO) {
        returnNumber(2000)
    }

    val num2 = async(Dispatchers.IO) {
        returnNumber(3000)
    }

    println("Дождались результаты async блоков ${awaitAll(num1, num2)}")
    println()

    println("Работа с Flow")
    returnFlow().collect { number ->
        println("Эмитящееся значение: $number")
    }
    println()

    println("Есть возможность преобразования потока, отлова ошибок, работа на других потоках и тп")
    returnFlowWithException()
        .map { number -> number * 10 }
        .onEach { number -> println("Эмитящееся значение: $number на потоке: ${Thread.currentThread().name}") }
        .catch { println("Вылетело исключение") }
        .flowOn(Dispatchers.IO)
        .collect()
}

suspend fun returnNumber(number: Long) = number.also {
    println("Имитация какого-то продолжительного дейсвия")
    delay(number)
}

fun returnFlow() = flow {
    repeat(5) { number ->
        emit(number)
        delay(500)
    }
}

fun returnFlowWithException() = flow {
    repeat(5) { number ->
        emit(number)
        if (number == 4) emit(10 / 0)
        delay(500)
    }
}