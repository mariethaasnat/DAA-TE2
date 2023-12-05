import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static Statistics run(Partitioner partitioner) {
        long memoryUsage = 0;
        long timeUsage = 0;
        long recursiveCalls = 0;

        try {
            long startTime = System.currentTimeMillis();
            memoryUsage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            partitioner.run();
            recursiveCalls = partitioner.getRecursiveCalls();
            memoryUsage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - memoryUsage;
            timeUsage = System.currentTimeMillis() - startTime;
        } catch (Exception e) {
            System.out.println(e);
        }
        return new Statistics(memoryUsage, timeUsage, recursiveCalls);
    }

    static int[] getArray(String fileName){
        List<Integer> list = new ArrayList<Integer>(80);
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            String line;
            while((line = in.readLine()) != null){
                list.add(Integer.parseInt(line));
            }
            in.close();
            return list.stream().mapToInt(i -> i).toArray();
        } catch(Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public static void main(String[] args) throws Exception{
        String fileName;
        Partitioner partitioner = null;
        // TODO : Choose file
        // fileName = "element1_10.txt";
        // fileName = "element2_40.txt";
        fileName = "element3_80.txt";

        int[] intArray = getArray(fileName);
        if (intArray == null) {
            System.out.println("Error: intArray is null");
            return;
        }

        // TODO: Choose partitioner
        // partitioner = new DynamicPartitioner(intArray);
         partitioner = new BranchBound(intArray);

        Statistics statistics = run(partitioner);

        System.out.println("Elapsed time   : " + statistics.timeUsage + " ms");
        System.out.println("Memory usage   : " + statistics.memoryUsage + " KB");
        System.out.println("Recursive calls: " + statistics.recursiveCalls);
    }
}