package ru.otus.protobuf.server;


import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NumbersServer {

    private static final Logger logger = LoggerFactory.getLogger(NumbersServer.class);

    private static final int SERVER_PORT = 8190;

    public static void main(String[] args) throws IOException, InterruptedException {
        var numbersServerService = new NumbersServerServiceImpl();
        Server server = ServerBuilder.forPort(SERVER_PORT).addService(numbersServerService).build();
        server.start();
        logger.info("NumbersServer waiting for client connections...");
        server.awaitTermination();
    }
}
