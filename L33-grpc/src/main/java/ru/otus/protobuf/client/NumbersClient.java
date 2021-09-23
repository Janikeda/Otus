package ru.otus.protobuf.client;

import io.grpc.ManagedChannelBuilder;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.protobuf.generated.AppMessage;
import ru.otus.protobuf.generated.NumbersServerServiceGrpc;

public class NumbersClient {

    private static final Logger logger = LoggerFactory.getLogger(NumbersClient.class);

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;

    public static void main(String[] args) throws InterruptedException {
        var channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
            .usePlaintext()
            .build();
        logger.info("NumbersClient is starting...");

        NumbersServerServiceGrpc.NumbersServerServiceStub stub = NumbersServerServiceGrpc
            .newStub(channel);

        int firstValue = 0;
        int lastValue = 30;
        var appMessage = AppMessage.newBuilder().setFirstNumber(firstValue).setLastNumber(lastValue)
            .build();

        int currentValue = 0;

        var responseObserver = new ClientStreamObserver();
        stub.getSequence(appMessage, responseObserver);

        for (int i = 0; i < 50; i++) {
            currentValue = currentValue + responseObserver.getLastValWithRefreshing() + 1;
            logger.info("currentValue: {}", currentValue);
            TimeUnit.MILLISECONDS.sleep(1000);
        }
        channel.shutdown();
        logger.info("NumbersClient was shutdown");
    }
}
