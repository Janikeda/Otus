package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.service.сache.EventType;
import ru.otus.service.сache.HwCache;
import ru.otus.service.сache.HwListener;
import ru.otus.service.сache.MyCache;

public class Demo {

    private static final Logger logger = LoggerFactory.getLogger(Demo.class);

    public static void main(String[] args) {
        new Demo().demo();
    }

    private void demo() {
        HwCache<String, Integer> cache = new MyCache<>(5);

        // пример, когда Idea предлагает упростить код, при этом может появиться "спец"-эффект
        HwListener<String, Integer> listener = new HwListener<String, Integer>() {
            @Override
            public void notify(String key, Integer value, EventType action) {
                logger.info("key:{}, value:{}, action: {}", key, value, action);
            }
        };

        cache.addListener(listener);
        cache.put("1", 1);

        logger.info("getValue:{}", cache.get("1"));
        cache.remove("1");
        cache.removeListener(listener);
    }
}
