# Lab 1: JAVA Sockets

## Sockets and Threads

It's common to use multiple threads to manage single and [multiple connections](https://www.geeksforgeeks.org/introducing-threads-socket-programming-java/).

For single connections, it's useful to have a thread that listens for incoming messages and another to manage outgoing messages so the program can send and receive messages at the same time without being blocked.

### Instance a Thread

There are different ways to instance threads in Java:

#### Extending the Thread class

```java
class ThreadDemo extends Thread {
    @Override
    public void run() {}
}
```

#### Passing a method as an argument

```java
Thread thread = new Thread(obj::method).start();
```

#### Using lambda functions

```java
Thread thread = new Thread(() -> { }).start();
```

## Objects in multi-threaded programs

The [Object Superclass](https://docs.oracle.com/javase/tutorial/java/IandI/objectclass.html) provides the `wait()` and `notify()` methods that can be used to synchronize threads. [More information](https://docs.oracle.com/javase/tutorial/java/IandI/objectclass.html).

- `wait()`: Waits for any other thread to call notify on the object to wake up the current thread.[Source](https://www.digitalocean.com/community/tutorials/java-thread-wait-notify-and-notifyall-example).

- `notify()`: Wakes up one of the threads waiting on the object. [Source](https://www.digitalocean.com/community/tutorials/java-thread-wait-notify-and-notifyall-example).

## Data Structures in multi-threaded programs

The `synchronized` keyword is used to prevent multiple threads from accessing the same data structure at the same time. [More information](https://www.baeldung.com/java-synchronized).
