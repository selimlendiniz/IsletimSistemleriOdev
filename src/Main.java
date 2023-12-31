public class Main {
    public static void main(String[] args) {

        Reader reader = new Reader("giris.txt");

        Assigner.allProcesses = reader.read();

        Assigner.run();

    }
}