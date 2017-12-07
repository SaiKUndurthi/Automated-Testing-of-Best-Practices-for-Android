import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * The Logging class provides with all the method definitiond and hashmaps requierd to generate the log for an Android application.
 */
public class Logging {

    private static final List<HashMap < Object, StackTraceElement[] >> maps = new ArrayList<HashMap < Object, StackTraceElement[] >>();
    private static final Logging logger = new Logging(2);
    
    private Logging(int numMaps) {
        for(int i = 0; i<numMaps; i++){
            HashMap < Object, StackTraceElement[] > hashMap = new HashMap < Object, StackTraceElement[] > ();
            maps.add(i, hashMap);
        }
    }


    // Returns Instance of the Logging class
    public static Logging getInstance() {
        return logger;
    }


    // Method to insert the stacktrace of the best practices that are to be released.
    public void addReference(int mapId, Object arg, StackTraceElement[] stacktrace) {
        if(0 <= mapId && mapId < maps.size()){
            maps.get(mapId).put(arg, stacktrace);
        }
    }


    // Method to remove stacktrace of the best practice from the hashmaps that followed all the rules.
    public void removeFromHashMap(int mapId, Object arg) {
            maps.get(mapId).remove(arg);
    }


    // This method prints the log for the best practices not followed.
    public void printingLog(int mapId) {
        if (!maps.get(mapId).isEmpty()) {
            for (Object listener: maps.get(mapId).keySet()) {
                StackTraceElement[] element = maps.get(mapId).get(listener);
                if (element[3].getMethodName().toString().equals("requestLocationUpdates")) {
                    System.out.println("Violating best practice. 'requestLocationUpdates' has been misused. Error 1");
                } else if (element[3].getMethodName().toString().equals("open")) {
                    System.out.println("Violating best practice. 'Camera open()' has been misused. Error 2");
                } else if (element[3].getMethodName().toString().equals("registerListener")) {
                    System.out.println("Violating best practice. 'registerListener' has been misused. Error 5");
                } else if (element[3].getMethodName().toString().equals("startService")) {
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


    // Clears the list and hashmaps.
    public void clearAll() {
        boolean empty = false;
        for(int i = 0; i< maps.size(); i++){
            if (maps.get(i).isEmpty()) {
                empty = true;
            }
        }
        if(empty){
            System.out.println("Followed all the best practices.");
            System.out.printf("-------------------------------------------------------------\n");
        }else {
            maps.clear();
        }        
    }
}