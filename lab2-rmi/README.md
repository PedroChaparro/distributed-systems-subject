# Second laboratory

## Statement

Write two programs in Java, client and server, that allow sending messages using the RMI mechanism.

## Approach

Write a server program in Java that exposes a "repository" object (skeleton) to create and filter songs. Then, write a client program to get a local instance of the remote "repository" (stub) and call the methods exposed by the skeleton.

- Check the [Songs Repository declaration](./rmi-server/src/shared/SongsRepository.java) to see the expected methods to be implemented / exposed by the server. 
- Check the [Song declaration](./rmi-server/src/shared/Song.java) to see the `Song` entity schema. 
