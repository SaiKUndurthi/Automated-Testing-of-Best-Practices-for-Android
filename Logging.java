import java.util.HashMap;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * The Logging class provides with all the method definitiond and hashmaps requierd to generate the log for an Android application.
 */
public class Logging {
    private static final HashMap < Object, StackTraceElement[] > onPauseLogStoringStruct = new HashMap < Object, StackTraceElement[] > ();   // Stores the stacktrace of the best practices that are to be relesed onPause.
    private static final HashMap < Object, StackTraceElement[] > onDestroyLogStoringStruct = new HashMap < Object, StackTraceElement[] > ();    // Stores the stacktrace of the best practices that are to be relesed onDestroy.
    private static final Logging logger = new Logging();
    private Logging() {}

    // Returns Instance of the Logging class
    public static Logging getInstance() {
        return logger;
    }

    // Method to insert the stacktrace of the best practices that are to be relesed onDestroy.
    public void insertToDestroyHashMap(Object arg, StackTraceElement[] stacktrace) {
            
            onDestroyLogStoringStruct.put(arg, stacktrace);
    }

    // Method to insert the stacktrace of the best practices that are to be relesed onPause.
    public void insertToPauseHashMap(Object arg, StackTraceElement[] stacktrace) {
        
            onPauseLogStoringStruct.put(arg, stacktrace);
        
    }


    // Method to remove stacktrace of the best practice from the hashmaps that followed all the rules.
    public void removeFromHashMap(Object arg) {
        if (onPauseLogStoringStruct.containsKey(arg)) {
            onPauseLogStoringStruct.remove(arg);
        } else if (onDestroyLogStoringStruct.containsKey(arg)) {
            onDestroyLogStoringStruct.remove(arg);
        }
    }

    // This method prints the log for the best practices not followed onPause.
    public void printingPauseLog() {
        if (!onPauseLogStoringStruct.isEmpty()) {
            for (Object listener: onPauseLogStoringStruct.keySet()) {
                StackTraceElement[] element = onPauseLogStoringStruct.get(listener);
                if (element[3].getMethodName().toString().equals("requestLocationUpdates")) {
                    System.out.println("Violating best practice. 'requestLocationUpdates' has been misused. Error 1");
                } else if (element[3].getMethodName().toString().equals("open")) {
                    System.out.println("Violating best practice. 'Camera open()' has been misused. Error 2");
                } else if (element[3].getMethodName().toString().equals("registerListener")) {
                    System.out.println("Violating best practice. 'registerListener' has been misused. Error 5");
                }
                for (int i = 3; i < element.length; i++) {
                    System.out.println("\t\tat " + element[i] + "\n");
                }
                System.out.printf("-------------------------------------------------------------\n");
            }
        }
    }

    // This method prints the log for the best practices not followed onDestroy.
    public void printingDestroyLog() {
        if (!onDestroyLogStoringStruct.isEmpty()) {
            for (Object listener: onDestroyLogStoringStruct.keySet()) {
                StackTraceElement[] element = onDestroyLogStoringStruct.get(listener);
                if (element[3].getMethodName().toString().equals("startService")) {
                    System.out.println("WARNING 3: 'startService' has been misused. Remember to call stopService.");
                } else if (element[3].getMethodName().toString().equals("accept")) {
                    System.out.println("Violating best practice. 'BluetoothServerSocket accept()' has been misused. Error 4");
                }
                for (int i = 3; i < element.length; i++) {
                    System.out.println("\t\tat " + element[i] + "\n");
                }
                System.out.printf("-------------------------------------------------------------\n");
            }
        }
    }

    // Clears onDestroyLogStoringStruct and onPauseLogStoringStruct hashmaps.
    public void onDestroyClearAll() {
        if (onPauseLogStoringStruct.isEmpty() && onDestroyLogStoringStruct.isEmpty()) {
            System.out.println("Followed all the best practices.");
            System.out.printf("-------------------------------------------------------------\n");
        } else {
            onPauseLogStoringStruct.clear();
            onDestroyLogStoringStruct.clear();
        }
    }
}