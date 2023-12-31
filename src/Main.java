public class Main {
    public static void main(String[] args) {

        Reader reader = new Reader("src/giris.txt");

        Assigner.allProcesses = reader.read();

        Assigner.run();




    }
}