package homework;

import com.sun.management.GarbageCollectionNotificationInfo;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;
import javax.management.MBeanServer;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;

public class GcAnalyzer {

    private static int gcYoungCount;
    private static int gcOldCount;

    private static long gcYoungAllDuration;
    private static long gcOldAllDuration;

    private static long gcYoungMaxDuration;
    private static long gcOldMaxDuration;

    private static long beginTime;

    public static void main(String[] args) throws Exception {
        System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());
        switchOnMonitoring();
        beginTime = System.currentTimeMillis();

        int loopCounter = 100_000;
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("homework:type=Benchmark");

        Benchmark mbean = new Benchmark(loopCounter);
        mbs.registerMBean(mbean, name);
        mbean.run();

        System.out.println("time:" + (System.currentTimeMillis() - beginTime) / 1000);

    }

    private static void switchOnMonitoring() {
        List<GarbageCollectorMXBean> gcbeans = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            System.out.println("GC name:" + gcbean.getName());
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            NotificationListener listener = (notification, handback) -> {
                if (notification.getType()
                    .equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo
                        .from((CompositeData) notification.getUserData());
                    String gcName = info.getGcName();
                    String gcAction = info.getGcAction();
                    String gcCause = info.getGcCause();

                    long startTime = info.getGcInfo().getStartTime();
                    long duration = info.getGcInfo().getDuration();

                    System.out.println(
                        "start:" + startTime + " Name:" + gcName + ", action:" + gcAction
                            + ", gcCause:" + gcCause + "(" + duration + " ms)");

                    countGcMetrics(gcAction, duration);
                    printStatistics();
                }
            };
            emitter.addNotificationListener(listener, null, null);
        }
    }

    private static void countGcMetrics(String gcAction, long duration) {
        if (gcAction.equals("end of minor GC")) {
            gcYoungCount++;
            gcYoungAllDuration = gcYoungAllDuration + duration;
            if (duration > gcYoungMaxDuration) {
                gcYoungMaxDuration = duration;
            }
        } else {
            gcOldCount++;
            gcOldAllDuration = gcOldAllDuration + duration;
            if (duration > gcOldMaxDuration) {
                gcOldMaxDuration = duration;
            }
        }
    }

    private static void printStatistics() {
        System.out
            .println("Amount of Young GC: " + gcYoungCount + ", Old GC: " + gcOldCount);
        System.out
            .println(
                "All duration (ms) of Young GC: " + gcYoungAllDuration + ", Old GC: "
                    + gcOldAllDuration);
        System.out.println(
            "Average duration (ms) of Young GC: " + gcYoungAllDuration / (
                gcYoungCount == 0
                    ? 1 : gcYoungCount)
                + ", Old GC: "
                + gcOldAllDuration / (gcOldCount == 0 ? 1 : gcOldCount));
        System.out.println(
            "Longest duration (ms) of Young GC: " + gcYoungMaxDuration + ", Old GC: "
                + gcOldMaxDuration);
        System.out.println("time:" + (System.currentTimeMillis() - beginTime) / 1000);
        System.out
            .println("Free memory: " + Runtime.getRuntime().freeMemory() / 1000000);
    }
}
