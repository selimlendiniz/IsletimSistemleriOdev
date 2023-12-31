
import java.nio.ByteBuffer;

public class Resources {

    static final int megabyte = 1024;
    static ByteBuffer buffer = ByteBuffer.allocateDirect(megabyte * 1024 * 1024);
    static Integer printer = 2;
    static Integer scanner = 1;
    static Integer modem = 1;
    static Integer disk = 2;
    static ByteBuffer realTimeSize = ByteBuffer.allocateDirect(megabyte * 64);
    static ByteBuffer userSize = ByteBuffer.allocateDirect(megabyte * 960);


    static final Integer maxPrinter = 2;
    static final Integer maxScanner = 1;
    static final Integer maxModem = 1;
    static final Integer maxDisk = 2;

    static final Integer maxRealTimeSize = 64;
    static final Integer maxUserSize = 960;


}