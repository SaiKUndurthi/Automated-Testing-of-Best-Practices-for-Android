# Best-Practices-for-Android-Apps

This tool analyzes and provides the feedback on an Android app, violating best practices. The analysis looks out for patterns that 
violate the best practices of Android and with respect to the violations generates log which helps the developer better understand 
their API usage. It logs the call stack of the Android API called to understand the class and method where the violation took place.
The logs generated are also helpful for profiling and identifying bugs. The tool can also be used efficiently and with minimal efforts
by users to examine the behavior of Android Apps.

### To use this tool, you'll need to instrument the source code using [Instrumentation_tool](https://github.com/poojakanchan/instrumentation_tool).
Follow step 1 "Establish build environment and Download Android source." from the User Guide document provided in the repository.
      
### Steps to build customized emulator.
	1)	After completing the build steps from [Instrumentation_tool](https://github.com/poojakanchan/instrumentation_tool), paste these	
			two files 'BestPracAspect.java' and 'Logging.java' under src/ folder.
	
	2)	'BestPracAspect.java' contains all the aspect implementation of the Android API's which are considered to be the Best practices.
			'Logging.java' has all the helper methods required to generate the log.
	
	3)	Initialize envieonment
			$ export ANDROID_HOME=<path to Working_Directory>
			$ source build/envsetup.sh
			$ lunch
				
	4)	Build Android source
			$ make showcommands
	  	To redirect logs to log file, run
			$ make showcommands 2>&1 | tee log_file
	  	To use multiple threads, run it with -j option as
			$ make -j4
	  	Note that, building the source may require up to 2-3 hours.
			 
	5)	After build is successful, launch emulator using command:
			$ emulator
	  	To see adb logs, use command:
			$ adb logcat
 
### Sample applications
Two APK files are provided under the apks/ folder for you to understand the working of the tool. "violate.apk" this application violates
the best practices and "followsBestPractice.apk" follows all the best practices.
