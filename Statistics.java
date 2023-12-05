public final class Statistics {
    long memoryUsage;
    long timeUsage;
    long recursiveCalls;
    public Statistics(long memoryUsage, long timeUsage, long recursiveCalls) {
        this.memoryUsage = memoryUsage;
        this.timeUsage = timeUsage;
        this.recursiveCalls = recursiveCalls;
    }
}
