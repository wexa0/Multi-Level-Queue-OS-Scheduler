package mlq;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Driver {

    private static final int MAX_PROCESSES = 100; // Assuming a maximum of 100 processes
    private static PCB[] Q1 = new PCB[MAX_PROCESSES];
    private static PCB[] Q2 = new PCB[MAX_PROCESSES];
    private static PCB[] schedulingOrder = new PCB[200];
    private static int q1Size = 0;
    private static int q2Size = 0;
    private static int orderSize = 0;
    static int numProcesses = 0;
    static int numOfProccesses = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean exit = false;

        while (!exit) {
            System.out.println("Main Menu:");
            System.out.println("1. Enter process information.");
            System.out.println("2. Report detailed information about each process and different scheduling criteria.");
            System.out.println("3. Exit the program.");
            System.out.println("Enter your choice (1, 2, or 3):");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    enterProcessInformation(scanner);
                    break;
                case 2:
                    executionProcess(); // Call executionProcess before generating the report
                    generateReport();
                    break;
                case 3:
                    System.out.println("Exiting program...");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1, 2, or 3.");
            }
        }

        scanner.close();
    }

    public static void enterProcessInformation(Scanner scanner) {
        System.out.println("Enter the number of processes:");
        numProcesses = scanner.nextInt();
        for (int i = 0; i < numProcesses; i++) {
            System.out.println();

            System.out.println("proccess p" + (i + 1) + ": ");
            System.out.println("Enter process priority (1 or 2):");
            int priority = scanner.nextInt();

            System.out.println("Enter arrival time:");
            int arrivalTime = scanner.nextInt();

            System.out.println("Enter CPU burst time:");
            int cpuBurst = scanner.nextInt();

            PCB process = new PCB("P" + (i + 1), priority, arrivalTime, cpuBurst);

            if (priority == 1) {
                if (q1Size < MAX_PROCESSES) {
                    Q1[q1Size++] = process;
                } else if (priority == 2) {
                    System.out.println("Q1 is full. Cannot add more processes.");
                }
            } else {
                if (q2Size < MAX_PROCESSES) {
                    Q2[q2Size++] = process;
                } else {
                    System.out.println("Q2 is full. Cannot add more processes.");
                }
            }

        }
        numOfProccesses = q1Size + q2Size;
    }

    
    private static void sortProcesses(PCB[] array, int size) {
        // Sorting Q1 or Q2 based on arrival time while maintaining original order
        stableSort(array, size);
    }

    public static void stableSort(PCB[] array, int size) {
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                // Compare arrival times
                if (array[j].getArrivalTime() > array[j + 1].getArrivalTime()) {
                    // Swap elements
                    PCB temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                } else if (array[j].getArrivalTime() == array[j + 1].getArrivalTime()) {
                    // If arrival times are equal, ensure original order is maintained
                    if (j > 0 && array[j - 1].getArrivalTime() == array[j].getArrivalTime()) {
                        // If the previous element also has the same arrival time, continue
                        continue;
                    }
                    if (j + 1 < size - 1 && array[j + 1].getArrivalTime() == array[j + 2].getArrivalTime()) {
                        // If the next element also has the same arrival time, swap
                        PCB temp = array[j];
                        array[j] = array[j + 1];
                        array[j + 1] = temp;
                    }
                }
            }
        }
    }

    public static void executionProcess() {
        int currentTime = 0;
        int quantum = 3;
        int q1Index = 0;
        int q2Index = 0;
        boolean isNull = (q1Size == 0 && q2Size == 0);

        if (isNull) {
            System.out.println("There are no processes to execute!" + "\n");
            return;
        }

        sortProcesses(Q1, q1Size); // Sort the processes in Q1 based on arrival time
        sortProcesses(Q2, q2Size); // Sort the processes in Q2 based on arrival time

        while (q1Index < q1Size || q2Index < q2Size) {
            // Process Q1
            if (q1Index < q1Size && (Q1[q1Index].getArrivalTime() <= currentTime)) {
                PCB currentProcess = Q1[q1Index];

                if (currentProcess.getResponseTime() == -1) {
                    currentProcess.setStartTime(currentTime);
                    currentProcess.setResponseTime(currentProcess.calculateResponseTime());
                }

                int remainingTime = currentProcess.getRemainingTime();

                if (remainingTime <= quantum) {
                    currentTime = currentTime + remainingTime;
                    currentProcess.setRemainingTime(0);
                    currentProcess.setTerminationTime(currentTime);
                    currentProcess.setTurnAroundTime(currentProcess.calculateTurnaroundTime());
                    currentProcess.setWaitingTime(currentProcess.calculateWaitingTime());
                    schedulingOrder[orderSize++] = currentProcess;
                } else {
                    currentTime = currentTime + quantum;
                    currentProcess.setRemainingTime(remainingTime - quantum);
                    schedulingOrder[orderSize++] = currentProcess;

                    if (currentProcess.getRemainingTime() > 0) {
                        // Find the position to insert the current process to continue execution 
                        int insertIndex = q1Size;
                        for (int i = q1Index; i < q1Size; i++) {
                            if (Q1[i].getArrivalTime() > currentTime) {
                                insertIndex = i;
                                break;
                            }
                        }
                        if (insertIndex != q1Size) {
                            for (int i = q1Size - 1; i >= insertIndex; i--) {
                                Q1[i + 1] = Q1[i];
                            }
                        }
                        // Add the current process at the determined index
                        Q1[insertIndex] = currentProcess;
                        q1Size++;
                    }
                }

                q1Index++; // Move to the next process in Q1
            }
            
            //Q2(Short Job First)
            int minBurstIndex = sortBurst(currentTime);
            if ((q2Index < q2Size && minBurstIndex != -1) && (q1Index >= q1Size || Q1[q1Index].getArrivalTime() > currentTime)) {
                PCB currentProcess = Q2[minBurstIndex];

                    currentProcess.setStartTime(currentTime);
                    currentProcess.setResponseTime(currentProcess.calculateResponseTime());
                    currentTime += currentProcess.getCPUBurst();
                    currentProcess.setTerminationTime(currentTime);
                    currentProcess.setTurnAroundTime(currentProcess.calculateTurnaroundTime());
                    currentProcess.setWaitingTime(currentProcess.calculateWaitingTime());
                    schedulingOrder[orderSize++] = currentProcess;
                    q2Index++; // Move to the next process in Q2
            }

            // Increment current time if no processes are ready
            if (q1Index < q1Size || q2Index < q2Size) {
                // Find the minimum arrival time among the next processes in Q1 and Q2
                if ((q1Index < q1Size && Q1[q1Index].getArrivalTime() > currentTime) && (q2Index < q2Size)) {
                    if (q2Index < q2Size && Q2[q2Index].getArrivalTime() > currentTime) {
                        if (Q2[q2Index].getArrivalTime() < Q2[q2Index].getArrivalTime()) {
                            currentTime = Q1[q1Index].getArrivalTime();
                        } else {
                            currentTime = Q2[q2Index].getArrivalTime();
                        }
                    }
                } else if ((q1Index < q1Size && Q1[q1Index].getArrivalTime() > currentTime) && (q2Index >= q2Size)) {
                    currentTime = Q1[q1Index].getArrivalTime();
                } else if (q2Index < q2Size && Q2[q2Index].getArrivalTime() > currentTime && (q1Index >= q1Size)) {
                    currentTime = Q2[q2Index].getArrivalTime();
                }
            }
        }
    }

    public static int sortBurst(int currentTime) {
    int minBurstIndex = -1; // Initialize to an invalid index
    for (int i = 0; i < q2Size; i++) {
        if (Q2[i].getResponseTime() == -1 && Q2[i].getArrivalTime() <= currentTime) {
            minBurstIndex = i;
            break;
        }
    }
    if (minBurstIndex != -1) {
        for (int i = minBurstIndex + 1; i < q2Size; i++) {
            if (Q2[i].getResponseTime() == -1 && Q2[i].getArrivalTime() <= currentTime &&
                    Q2[i].getCPUBurst() < Q2[minBurstIndex].getCPUBurst()) {
                minBurstIndex = i;
            }
        }
    }
    return minBurstIndex;
    }

        
    public static void generateReport() {
        if (schedulingOrder != null && orderSize > 0) {
            try (FileWriter writer = new FileWriter("Report.txt")) {

                // Grantt Chart
                StringBuilder schedulingOrderStr = new StringBuilder("Scheduling order of the processes: [");
                for (int i = 0; i < orderSize; i++) {
                    schedulingOrderStr.append(schedulingOrder[i].getProcessID());
                    if (i < orderSize - 1) {
                        schedulingOrderStr.append(" | ");
                    }
                }
                schedulingOrderStr.append("]\n");
                System.out.println(schedulingOrderStr);
                writer.write(schedulingOrderStr.toString());

                Set<String> printedProcesses = new HashSet<>();
                double totalTurnaroundTime = 0;
                double totalWaitingTime = 0;
                double totalResponseTime = 0;

                for (int i = 0; i < orderSize; i++) {
                    PCB process = schedulingOrder[i];
                    String processID = process.getProcessID();
                    if (!printedProcesses.contains(processID)) {
                        System.out.println("\nProcess ID: " + processID);
                        System.out.println("Priority: " + process.getPriority());
                        System.out.println("Arrival time: " + process.getArrivalTime());
                        System.out.println("CPU burst: " + process.getCPUBurst());
                        System.out.println("Start time: " + process.getStartTime());
                        System.out.println("Termination time: " + process.getTerminationTime());
                        System.out.println("Turnaround time: " + process.getTurnAroundTime());
                        System.out.println("Waiting time: " + process.getWaitingTime());
                        System.out.println("Response time: " + process.getResponseTime());

                        writer.write("\nProcess ID: " + processID + "\n");
                        writer.write("Priority: " + process.getPriority() + "\n");
                        writer.write("Arrival time: " + process.getArrivalTime() + "\n");
                        writer.write("CPU burst: " + process.getCPUBurst() + "\n");
                        writer.write("Start time: " + process.getStartTime() + "\n");
                        writer.write("Termination time: " + process.getTerminationTime() + "\n");
                        writer.write("Turnaround time: " + process.getTurnAroundTime() + "\n");
                        writer.write("Waiting time: " + process.getWaitingTime() + "\n");
                        writer.write("Response time: " + process.getResponseTime() + "\n\n");

                        totalTurnaroundTime += process.getTurnAroundTime();
                        totalWaitingTime += process.getWaitingTime();
                        totalResponseTime += process.getResponseTime();

                        printedProcesses.add(processID);
                    }
                }

                double avgTurnaroundTime = totalTurnaroundTime / numOfProccesses;
                double avgWaitingTime = totalWaitingTime / numOfProccesses;
                double avgResponseTime = totalResponseTime / numOfProccesses;

                System.out.println("\nAverage Turnaround Time: " + avgTurnaroundTime);
                System.out.println("Average Waiting Time: " + avgWaitingTime);
                System.out.println("Average Response Time: " + avgResponseTime);

                writer.write("\nAverage Turnaround Time: " + avgTurnaroundTime + "\n");
                writer.write("Average Waiting Time: " + avgWaitingTime + "\n");
                writer.write("Average Response Time: " + avgResponseTime + "\n");

            } catch (IOException e) {
                System.out.println("An error occurred while writing to the file.");
                e.printStackTrace();
            }
            // Clear Q1 and Q2 after generating the report
            q1Size = 0;
            q2Size = 0;
            orderSize = 0;
        }
    }
}
