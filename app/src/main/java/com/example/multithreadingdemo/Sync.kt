package com.example.multithreadingdemo

fun main() {
    var run1 = Runnable {
        syncFunWithSynchronized(1000)
    }

    var run2 = Runnable {
        syncFunWithSynchronized(1200)
    }

    var thread1 = Thread(run1)
    var thread2 = Thread(run2)
    thread1.start()
    thread2.start()

    thread1.join()
    thread2.join()
    println()

    run1 = Runnable {
        syncFun(1000)
    }

    run2 = Runnable {
        syncFun(1200)
    }

    thread1 = Thread(run1)
    thread2 = Thread(run2)
    thread1.start()
    thread2.start()

    thread1.join()
    thread2.join()
    println()

    run1 = Runnable {
        syncFunWithSynchronizedBLock(1000)
    }

    run2 = Runnable {
        syncFunWithSynchronizedBLock(1200)
    }

    thread1 = Thread(run1)
    thread2 = Thread(run2)
    thread1.start()
    thread2.start()
}

@Synchronized
fun syncFunWithSynchronized(time: Long) {
    for (i in 0..4) {
        println(
            "Производятся какие-то манипуляции, которые должны происходить"
                    + " единовременно только в одном потоке: ${Thread.currentThread().name}"
        )
        Thread.sleep(time)
    }
}

fun syncFun(time: Long) {
    for (i in 0..4) {
        println("Производятся какие-то манипуляции на потоке: ${Thread.currentThread().name}")
        Thread.sleep(time)
    }
}

val LOCK = Any()
fun syncFunWithSynchronizedBLock(time: Long) {
    println("Поток: ${Thread.currentThread().name} начал выполнение функции")
    synchronized(LOCK) {
        for (i in 0..4) {
            println(
                "Производятся какие-то манипуляции, которые должны происходить"
                        + " единовременно только в одном потоке: ${Thread.currentThread().name}"
            )
            Thread.sleep(time)
        }
    }
}