package ru.otus.protobuf.server;

import io.grpc.stub.StreamObserver;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.protobuf.generated.AppMessage;
import ru.otus.protobuf.generated.BasicMessage;
import ru.otus.protobuf.generated.NumbersServerServiceGrpc;

public class NumbersServerServiceImpl extends NumbersServerServiceGrpc.NumbersServerServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(NumbersServerServiceImpl.class);

    @Override
    public void getSequence(AppMessage appMessage, StreamObserver<BasicMessage> responseObserver) {

        int firstNumber = appMessage.getFirstNumber();
        int lastNumber = appMessage.getLastNumber();

        for (int i = firstNumber + 1; i <= lastNumber; i++) {
            responseObserver.onNext(BasicMessage.newBuilder().setValue(i).build());
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        }
        responseObserver.onCompleted();
    }
}
