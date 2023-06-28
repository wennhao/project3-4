import com.fazecast.jSerialComm.SerialPort;

public class SerialManager {
    private static SerialPort selectedPort;

    public static void openSerialPort(String portName) {
        if (selectedPort != null && selectedPort.isOpen()) {
            selectedPort.closePort();
        }
        selectedPort = SerialPort.getCommPort(portName);
        if (selectedPort.openPort()) {
            System.out.println("Serial port opened successfully");
        } else {
            System.out.println("Failed to open the serial port");
        }
    }

    public static SerialPort getSerialPort() {
        return selectedPort;
    }

    public static void closeSerialPort() {
        if (selectedPort != null && selectedPort.isOpen()) {
            selectedPort.closePort();
            System.out.println("Serial port closed");
        }
    }
}
