import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class Assigner {

    public static Queue<Process> allProcesses = new LinkedList<>();
    static Queue<Process> realTimeProcessesQueue = new LinkedList<>();
    static Queue<Process> priority1Queue = new LinkedList<>();
    static Queue<Process> priority2Queue = new LinkedList<>();
    static Queue<Process> priority3Queue = new LinkedList<>();
    static Queue<Process> waitingQueue = new LinkedList<>();

    public static long time = 0;


    public static void run() {

        while (!allProcesses.isEmpty() || !realTimeProcessesQueue.isEmpty() || !priority1Queue.isEmpty() || !priority2Queue.isEmpty() || !priority3Queue.isEmpty() || !waitingQueue.isEmpty()) {



            ifResourcesEnoughMoveQueueByPriority();

            categorizeProcess();





            // Real Time Queue
            if (!realTimeProcessesQueue.isEmpty()) {
                // Fcfs
                realTimeProcessesQueue.peek().run();

                if (realTimeProcessesQueue.peek().isFinished()) {
                    increaseResources(realTimeProcessesQueue.peek());
                    realTimeProcessesQueue.poll();


                }


                continue;

            }
            // Priority 1 Queue
            else if (!priority1Queue.isEmpty()) {
                priority1Queue.peek().run();

                if (priority1Queue.peek().isFinished()) {
                    increaseResources(priority1Queue.peek());
                    priority1Queue.poll();
                } else {
                    priority1Queue.peek().reducePriority();
                    priority2Queue.add(priority1Queue.poll());
                }


                continue;
            }
            // Priority 2 Queue
            else if (!priority2Queue.isEmpty()) {
                priority2Queue.peek().run();

                if (priority2Queue.peek().isFinished()) {
                    increaseResources(priority2Queue.peek());
                    priority2Queue.poll();
                } else {
                    priority2Queue.peek().reducePriority();
                    priority3Queue.add(priority2Queue.poll());
                }

                continue;
            }
            // Priority 3 Queue
            else if (!priority3Queue.isEmpty()) {

                //Round Robin

                priority3Queue.peek().run();

                if (priority3Queue.peek().isFinished()) {
                    increaseResources(priority3Queue.peek());
                    priority3Queue.poll();

                    if (priority3Queue.isEmpty()) {
                        continue;
                    }
                }

                priority3Queue.add(priority3Queue.poll());


                continue;
            }
            else {
                increaseWaitingTime3();
                ++Assigner.time;
            }






        }


    }


    private static void categorizeProcess() {

        if (allProcesses.isEmpty()) {
            return;
        }




        while (allProcesses.peek().getArrivalTime() <= time) {

            Process currentProcess = allProcesses.peek();

            if (currentProcess.getPrinter() <= Resources.maxPrinter &&
                    currentProcess.getScanner() <= Resources.maxScanner &&
                    currentProcess.getModem() <= Resources.maxModem &&
                    currentProcess.getDisk() <= Resources.maxDisk
            ) {

                // real time process
                if (currentProcess.getPriority() == 0) {

                    if (currentProcess.getSize() > Resources.maxRealTimeSize) {
                        System.out.println("HATA - Real Time Proses çok büyük" + " process id " + currentProcess.getProcessId());
                        //++Assigner.time;
                        allProcesses.poll();
                        continue;
                    }
                    realProcessAndAllocateResources(realTimeProcessesQueue);

                }
                // User Time Process
                else if (currentProcess.getPriority() == 1) {

                    if (currentProcess.getSize() > Resources.maxUserSize) {
                        System.out.println("HATA - User Proses çok büyük" + " process id " + currentProcess.getProcessId());
                        //++Assigner.time;
                        allProcesses.poll();
                        continue;
                    }

                    userProcessAndAllocateResources(priority1Queue);

                } else if (currentProcess.getPriority() == 2) {

                    if (currentProcess.getSize() > Resources.maxUserSize) {
                        System.out.println("HATA - User Proses çok büyük" + " process id " + currentProcess.getProcessId());
                        //++Assigner.time;
                        allProcesses.poll();
                        continue;
                    }


                    userProcessAndAllocateResources(priority2Queue);

                } else if (currentProcess.getPriority() == 3) {

                    if (currentProcess.getSize() > Resources.maxUserSize) {
                        System.out.println("HATA - User Proses çok büyük" + " process id " + currentProcess.getProcessId());

                        allProcesses.poll();
                        continue;
                    }

                    userProcessAndAllocateResources(priority3Queue);

                }

            } else {
                System.out.println("HATA - Proses çok sayıda kaynak talep ediyor" + " process id " + currentProcess.getProcessId());


                allProcesses.poll();
            }

            if (allProcesses.isEmpty()) {
                return;
            }
        }




    }

    public static void ifResourcesEnoughMoveQueueByPriority() {
        Iterator<Process> iterator = waitingQueue.iterator();
        while (iterator.hasNext()) {
            Process process = iterator.next();

            if (process.getPriority() == 1) {
                if (checkResources(process, Resources.userSize, Resources.printer, Resources.scanner, Resources.modem, Resources.disk)) {
                    reduceResources(process);
                    priority1Queue.add(process);
                    iterator.remove();
                }
            } else if (process.getPriority() == 2) {
                if (checkResources(process, Resources.userSize, Resources.printer, Resources.scanner, Resources.modem, Resources.disk)) {
                    reduceResources(process);
                    priority2Queue.add(process);
                    iterator.remove();
                }
            } else if (process.getPriority() == 3) {
                if (checkResources(process, Resources.userSize, Resources.printer, Resources.scanner, Resources.modem, Resources.disk)) {
                    reduceResources(process);
                    priority3Queue.add(process);
                    iterator.remove();
                }
            } else if (process.getPriority() == 0) {
                if (checkResources(process, Resources.realTimeSize, Resources.printer, Resources.scanner, Resources.modem, Resources.disk)) {
                    reduceResources(process);
                    realTimeProcessesQueue.add(process);
                    iterator.remove();
                }
            }
        }
    }

    private static boolean checkResources(Process process, ByteBuffer userSize, int printer, int scanner, int modem, int disk) {
        return process.getSize() <= userSize.capacity() &&
                process.getPrinter() <= printer &&
                process.getScanner() <= scanner &&
                process.getModem() <= modem &&
                process.getDisk() <= disk;
    }


    public static void reduceResources(Process process) {
        Resources.printer -= process.getPrinter();
        Resources.scanner -= process.getScanner();
        Resources.modem -= process.getModem();
        Resources.disk -= process.getDisk();

        for (int i = 0; i < process.getSize(); i++) {
            Resources.userSize.put((byte) i);
        }

    }

    public static void increaseResources(Process process) {
        Resources.printer += process.getPrinter();
        Resources.scanner += process.getScanner();
        Resources.modem += process.getModem();
        Resources.disk += process.getDisk();

    }


    public static void userProcessAndAllocateResources(Queue<Process> queue) {
        if (!allProcesses.isEmpty()) {
            Process currentProcess = allProcesses.peek();

            if (currentProcess.getSize() > Resources.userSize.capacity() ||
                    currentProcess.getPrinter() > Resources.printer ||
                    currentProcess.getScanner() > Resources.scanner ||
                    currentProcess.getModem() > Resources.modem ||
                    currentProcess.getDisk() > Resources.disk) {
                waitingQueue.add(allProcesses.poll());

            } else {
                reduceResources(currentProcess);
                queue.add(allProcesses.poll());


            }
        }
    }

    public static void realProcessAndAllocateResources(Queue<Process> queue) {
        if (!allProcesses.isEmpty()) {
            Process currentProcess = allProcesses.peek();

            if (currentProcess.getSize() > Resources.realTimeSize.capacity()) {
                waitingQueue.add(allProcesses.poll());

            } else {
                reduceResources(currentProcess);
                queue.add(allProcesses.poll());
            }
        }
    }


    public static void increaseWaitingTime2(Queue<Process> queue){
        Iterator<Process> iterator = queue.iterator();
        while (iterator.hasNext()) {
            Process process = iterator.next();
            process.setWaitingTime(process.getWaitingTime() + 1);

            if (process.getWaitingTime() >= 20) {
                System.out.println("HATA - Proses çok uzun süredir bekliyor 20 saniye, process id " + process.getProcessId());
                iterator.remove(); // Iterator üzerinden kaldırma işlemi güvenli
            }
        }
    }

    public static void increaseWaitingTime4(Queue<Process> queue){
        Iterator<Process> iterator = queue.iterator();
        while (iterator.hasNext()) {
            Process process = iterator.next();
            process.setWaitingTime(process.getWaitingTime() + 1);

            if (process.getWaitingTime() >= 20) {
                System.out.println("HATA - Proses çok uzun süredir bekliyor 20 saniye, process id " + process.getProcessId());
                increaseResources(process);
                iterator.remove(); // Iterator üzerinden kaldırma işlemi güvenli
            }

        }
    }

    public static void increaseWaitingTime3(){
        increaseWaitingTime4(realTimeProcessesQueue);
        increaseWaitingTime4(priority1Queue);
        increaseWaitingTime4(priority2Queue);
        increaseWaitingTime4(priority3Queue);
        increaseWaitingTime2(waitingQueue);
    }



}
