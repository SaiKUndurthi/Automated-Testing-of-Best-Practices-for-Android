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
 private static final HashMap < Object, String > printErrorStatement = new HashMap < Object, String > ();   // Stores the error statement to be logged when a best practice is violated.
 private static final HashMap < Object, StackTraceElement[] > onDestroyLogStoringStruct = new HashMap < Object, StackTraceElement[] > ();    // Stores the stacktrace of the best practices that are to be relesed onDestroy.
 private static final Logging logger = new Logging();
 private Logging() {}

// Returns Instance of the Logging class
 public static Logging getInstance() {
  return logger;
 }

 // Method to insert the stacktrace of the best practices that are to be relesed onDestroy.
 public void insertToDestroyHashMap(Object arg, StackTraceElement[] stacktrace) {
  if (onDestroyLogStoringStruct.isEmpty()) {
   onDestroyLogStoringStruct.put(arg, stacktrace);
  } else if (!onDestroyLogStoringStruct.containsKey(arg)) {
   onDestroyLogStoringStruct.put(arg, stacktrace);
  }
 }

 // Method to insert the stacktrace of the best practices that are to be relesed onPause.
 public void insertToPauseHashMap(Object arg, StackTraceElement[] stacktrace) {
  if (onPauseLogStoringStruct.isEmpty()) {
   onPauseLogStoringStruct.put(arg, stacktrace);

  } else if (!onPauseLogStoringStruct.containsKey(arg)) {
   onPauseLogStoringStruct.put(arg, stacktrace);
  }
 }

 // Method to insert the error statement that is logged when a best practice is violated.
 public void insertToErrorHashMap(Object arg, String error) {
  if (printErrorStatement.isEmpty()) {
   printErrorStatement.put(arg, error);
  } else if (!printErrorStatement.containsKey(arg)) {
   printErrorStatement.put(arg, error);
  }
 }

 // Method to remove stacktrace and error statement of the best practice from the hashmaps that followed all the rules.
 public void removeFromHashMap(Object arg) {
  if (onPauseLogStoringStruct.containsKey(arg)) {
   onPauseLogStoringStruct.remove(arg);
   printErrorStatement.remove(arg);
  } else if (onDestroyLogStoringStruct.containsKey(arg)) {
   onDestroyLogStoringStruct.remove(arg);
   printErrorStatement.remove(arg);
  }
 }

 // This method prints the log for the best practices not followed onPause.
 public void printingPauseLog() {
  if (!onPauseLogStoringStruct.isEmpty()) {
   for (Object listener: onPauseLogStoringStruct.keySet()) {
    StackTraceElement[] element = onPauseLogStoringStruct.get(listener);
    System.out.println(printErrorStatement.get(listener));
    for (int i = 0; i < element.length; i++) {
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
    System.out.println(printErrorStatement.get(listener));
    for (int i = 0; i < element.length; i++) {
     System.out.println("\t\tat " + element[i] + "\n");
    }
    System.out.printf("-------------------------------------------------------------\n");
   }
  }
 }

 // Clears onDestroyLogStoringStruct, onPauseLogStoringStruct and printErrorStatement hashmaps.
 public void onDestroyClearAll() {
   onPauseLogStoringStruct.clear();
   onDestroyLogStoringStruct.clear();
  if (printErrorStatement.isEmpty()) {
   System.out.println("Followed all the best practices.");
   System.out.printf("-------------------------------------------------------------\n");
  } else {
   printErrorStatement.clear();
  }
 }
