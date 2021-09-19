package ru.otus.protobuf.client;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.protobuf.generated.BasicMessage;

public class ClientStreamObserver implements StreamObserver<BasicMessage> {

    private static final Logger logger = LoggerFactory.getLogger(ClientStreamObserver.class);

    private int lastVal = 0;

    @Override
    public void onNext(BasicMessage value) {
        this.lastVal = value.getValue();
        logger.info("new value: {}", lastVal);
    }

    @Override
    public void onError(Throwable t) {
        logger.error(t.getMessage(), t);
    }

    @Override
    public void onCompleted() {
        logger.info("ClientStreamObserver job is done");
    }

    int getLastValWithRefreshing() {
        int result = this.lastVal;
        this.lastVal = 0;
        return result;
    }
}
