import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;

/**
 * The BestPracAspect class provides with all the well-defined aspect implemented best practices.
 */
@Aspect
public class BestPracAspect {

    // Instance of the Logging class
    Logging logger = Logging.getInstance();

    // Applying Before aspect on the requestLocationUpdates() API. API signature: requestLocationUpdates(String provider, long minTime, float minDistance, LocationListener listener).
    @Before("execution (* android.location.LocationManager.requestLocationUpdates(..))")
    public void beforeRequestLocationUpdates(JoinPoint joinPoint) {

        Object arg[] = joinPoint.getArgs();
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        logger.insertToPauseHashMap(arg[3], stacktrace); /* The 4th argument is always the LocationListener object, which is used as a key to keep track if it's removed after use. */
    }

    // Applying Before aspect on the Camera open() API. API signature: open ().
    @Before("execution (* android.hardware.Camera.open(..))")
    public void beforeCameraOpen(JoinPoint joinPoint) {

        Object arg = joinPoint.getThis();
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        logger.insertToPauseHashMap(arg, stacktrace); /* The Camera instance calling this method is used as a key to keep track if it's removed after use. */
    }


    // Applying Before aspect on the accept() API. API signature: BluetoothServerSocket.accept(int timeout).
    @Before("execution (* android.bluetooth.BluetoothServerSocket.accept(..))")
    public void beforeAccept(JoinPoint joinPoint) {

        Object arg = joinPoint.getThis();
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        logger.insertToDestroyHashMap(arg, stacktrace); /* The BluetoothServerSocket calling this method is used as a key to keep track if it's removed after use. */
    }

    // Applying Before aspect on the registerListener() API. API signature: registerListener(SensorEventListener listener, Sensor sensor, int samplingPeriodUs).
    @Before("execution (* android.hardware.SensorManager.registerListener(..))")
    public void beforeRegisterListener(JoinPoint joinPoint) {

        Object arg[] = joinPoint.getArgs();
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        logger.insertToPauseHashMap(arg[0], stacktrace); /* The 1st argument is always the SensorEventListener object, which is used as a key to keep track if it's removed after use. */
    }

    // Applying Before aspect on the onStartCommand() API. API signature: onStartCommand(Intent intent, int flags, int startId).
    @Before("execution (* android.app.Service.onStartCommand(..))")
    public void beforeonStartCommand(JoinPoint joinPoint) {

        Object arg[] = joinPoint.getArgs();
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        logger.insertToDestroyHashMap(arg[2], stacktrace);
    }

    // Applying Before aspect on the startService() API. API signature: startService(Intent service).
    @Before("execution (* android.content.Context.startService(..))")
    public void beforeStartService(JoinPoint joinPoint) {

        Object arg[] = joinPoint.getArgs();
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        logger.insertToDestroyHashMap(arg[0], stacktrace);
    }

    // Applying After aspect on the removeUpdates() API. API signature: removeUpdates (LocationListener listener).
    @After("execution (* android.location.LocationManager.removeUpdates(..))")
    public void afterRemoveUpdates(JoinPoint joinPoint) {

        Object arg[] = joinPoint.getArgs();
        logger.removeFromHashMap(arg[0]); /* The 1st argument is always the LocationListener object, which has to be removed at the end. */
    }

    // Applying After aspect on the release() API. API signature: release ().
    @After("execution (* android.hardware.Camera.release(..))")
    public void afterCameraRelease(JoinPoint joinPoint) {

        Object arg = joinPoint.getThis();
        logger.removeFromHashMap(arg); /* The Camera instance calling this method has to be removed after use. */
    }


    // Applying After aspect on the close() API. API signature: BluetoothServerSocket.close ().
    @After("execution (* android.bluetooth.BluetoothServerSocket.close(..))")
    public void afterClose(JoinPoint joinPoint) {

        Object arg = joinPoint.getThis();
        logger.removeFromHashMap(arg); /* The BluetoothServerSocket instance calling this method has to be removed after use. */
    }

    // Applying After aspect on the unregisterListener() API. API signature: unregisterListener(SensorEventListener listener).
    @After("execution (* android.hardware.SensorManager.unregisterListener(..))")
    public void afterUnregisterListener(JoinPoint joinPoint) {

        Object arg[] = joinPoint.getArgs();
        logger.removeFromHashMap(arg[0]); /* The 1st argument is always the SensorEventListener object, which has to be removed at the end. */
    }

    // Applying After aspect on the stopService() API. API signature: stopService(Intent service).
    @After("execution (* android.content.Context.stopService(..))")
    public void afterStopService(JoinPoint joinPoint) {

        Object arg[] = joinPoint.getArgs();
        logger.removeFromHashMap(arg[0]); /* The 1st argument is always the Intent object, which has to be removed at the end. */
    }

    // Applying After aspect on the stopSelf() API. API signature: stopSelf (int startId).
    @After("execution (* android.app.Service.stopSelf(..))")
    public void afterStopSelf(JoinPoint joinPoint) {

        Object arg[] = joinPoint.getArgs();
        logger.removeFromHashMap(arg[0]); /* The 1st argument is always the startId, which has to be removed at the end. */
    }

    // Applying Before aspect on the onStop() API. This implementation checks if all the best practices after onPause() callback are followed or not.
    @Before("execution (* android.app.Activity.onStop(..))")
    public void beforeOnStop(JoinPoint joinPoint) {

        logger.printingPauseLog(); /* This function prints the log according to the best practices followed or not after onPause. */
    }


    // Applying After aspect on the handleDestroyActivity() API. This implementation checks if all the best practices after onDestroy() callback are followed or not.
    @After("execution (* android.app.ActivityThread.handleDestroyActivity(..))")
    public void afterOnDestroy(JoinPoint joinPoint) {

        logger.printingDestroyLog(); /* This function prints the log according to the best practices followed or not after onDestroy. */
        logger.onDestroyClearAll(); /* Clears the onPause and onDestroy hashmap. */
    }

}