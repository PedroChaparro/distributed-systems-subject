# Sequence diagram

```mermaid
sequenceDiagram
    participant Client
    participant Server

    Note over Client, Server: RMI Registry
    Client ->> Server: Request to get a local instance (stub) of the remote object (skeleton)
    Server ->> Client: Return the stub

    Note over Client, Server: Remote method invocation
    Client ->> Client: Call a remote method using the stub
    Client ->> Client: The stub marshals the arguments
    Client ->> Server: The marshalled arguments are sent through the network

    Server ->> Server: The skeleton unmarshals the arguments
    Server ->> Server: Execute the method
    Server ->> Server: The skeleton marshals the return value
    Server ->> Client: The marshalled results are sent through the network

    Client ->> Client: The stub unmarshals the return value
```

## References

1. [Distributed Objects and Java RMI basics](http://si.deis.unical.it/~talia/aa0304/dis/es1-1p.pdf)
2. [Oracle | An Overview of RMI Applications](https://docs.oracle.com/javase/tutorial/rmi/overview.html)