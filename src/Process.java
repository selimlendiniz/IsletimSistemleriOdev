import java.util.Objects;

public class Process {

    private static Integer processIdCount = -1;
    private Integer processId;
    private Integer arrivalTime;
    private Integer priority;
    private Integer processTime;
    private Integer size;
    private Integer printer;
    private Integer scanner;
    private Integer modem;
    private Integer disk;
    private Integer complateTime;

    private Integer waitingTime;

    public void run() {

        ++complateTime;

        //System.out.println("Process is running process id " + processId + " Complate " + complateTime + "/" + processTime + " time " + Assigner.time);

        try {
            Thread.sleep(1); // 1000 milisaniye (1 saniye) beklet
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        ++Assigner.time;
        Assigner.increaseWaitingTime3();


        if (complateTime.equals(processTime)) {
            System.out.println("Process is finished" + " process id " + processId);

        }

    }

    public boolean isFinished() {
        return Objects.equals(processTime, complateTime);
    }

    public Integer reducePriority() {
        return ++priority;
    }

    public Process(Integer arrivalTime, Integer priority, Integer processTime, Integer size, Integer printer, Integer scanner, Integer modem, Integer disk) {
        this.arrivalTime = arrivalTime;
        this.priority = priority;
        this.processTime = processTime;
        this.size = size;
        this.printer = printer;
        this.scanner = scanner;
        this.modem = modem;
        this.disk = disk;
        this.processId = ++processIdCount;
        this.complateTime = 0;
        this.waitingTime = 0;
    }

    public static Integer getProcessIdCount() {
        return processIdCount;
    }

    public static void setProcessIdCount(Integer processIdCount) {
        Process.processIdCount = processIdCount;
    }

    public Integer getProcessId() {
        return processId;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }

    public Integer getComplateTime() {
        return complateTime;
    }

    public void setComplateTime(Integer complateTime) {
        this.complateTime = complateTime;
    }

    public Integer getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(Integer waitingTime) {
        this.waitingTime = waitingTime;
    }

    @Override
    public String toString() {
        return "Process{" +
                "arrivalTime=" + arrivalTime +
                ", priority=" + priority +
                ", processTime=" + processTime +
                ", size=" + size +
                ", printer=" + printer +
                ", scanner=" + scanner +
                ", modem=" + modem +
                ", disk=" + disk +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Process process = (Process) o;
        return Objects.equals(arrivalTime, process.arrivalTime) && Objects.equals(priority, process.priority) && Objects.equals(processTime, process.processTime) && Objects.equals(size, process.size) && Objects.equals(printer, process.printer) && Objects.equals(scanner, process.scanner) && Objects.equals(modem, process.modem) && Objects.equals(disk, process.disk);
    }

    @Override
    public int hashCode() {
        return Objects.hash(arrivalTime, priority, processTime, size, printer, scanner, modem, disk);
    }

    public Integer getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Integer arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getProcessTime() {
        return processTime;
    }

    public void setProcessTime(Integer processTime) {
        this.processTime = processTime;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getPrinter() {
        return printer;
    }

    public void setPrinter(Integer printer) {
        this.printer = printer;
    }

    public Integer getScanner() {
        return scanner;
    }

    public void setScanner(Integer scanner) {
        this.scanner = scanner;
    }

    public Integer getModem() {
        return modem;
    }

    public void setModem(Integer modem) {
        this.modem = modem;
    }

    public Integer getDisk() {
        return disk;
    }

    public void setDisk(Integer disk) {
        this.disk = disk;
    }
}
