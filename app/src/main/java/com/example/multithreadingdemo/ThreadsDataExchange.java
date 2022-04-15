package com.example.multithreadingdemo;

public class ThreadsDataExchange {

    public static void main(String[] args) {
        Result msg = new Result();

        Waiter waiter = new Waiter(msg);
        new Thread(waiter).start();

        Notifier notifier = new Notifier(msg);
        new Thread(notifier).start();
    }
}

class Result {

    private int result;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}

class Waiter implements Runnable {

    private final Result result;

    public Waiter(Result result) {
        this.result = result;
    }

    @Override
    public void run() {
        synchronized (result) {
            try {
                System.out.println("Поток Waiter дошел до момента, когда ему нужен результат из другого потока");
                result.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Получен результат из потока Notifier:" + result.getResult());
        }
    }
}

class Notifier implements Runnable {

    private final Result result;

    public Notifier(Result result) {
        this.result = result;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
            synchronized (result) {
                System.out.println("Какая-то сложная операция");
                result.setResult(1256735678);
                result.notify();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}