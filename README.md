# Best-Practices-for-Android-Apps

This tool will analyze and provide the feedback on Android’s best practices. This analysis looks out for patterns that violates the best practices and feedback will help the developer to improve the performance of the battery by rectifying bad design. It analyzes the call stack of Android API to understand method and class usage. The log files generated are also helpful for profiling, identifying bugs and execution time bottlenecks in the program execution. The tool can also be used by users to examine the behavior of Android Apps. For example, to find out if Android app is misusing API calls, application level access is not sufficient. In such scenario, this tool can be used efficiently and with minimal efforts.

### To use this tool, you'll need to instrument the source code using [Instrumentation_tool](https://github.com/poojakanchan/instrumentation_tool). Follow step 1 "Establish build environment and Download Android source." from the documenation.

	1)	Download folder “instrumentation_tool” from git repository into Working_Directory
			$ git clone https://github.com/poojakanchan/instrumentation_tool.git
      
### Steps to instrument.
	1)	After completing the build steps from [Instrumentation_tool](https://github.com/poojakanchan/instrumentation_tool), paste the       		'BestPracAspect.java' and 'Logging.java' files under src/ folder.
	
	2)	Initialize envieonment
			$ export ANDROID_HOME=<path to Working_Directory>
			$ source build/envsetup.sh
			$ lunch
				
	3)	Build Android source
			$ make showcommands
	  	To redirect logs to log file, run
			$ make showcommands 2>&1 | tee log_file
	  	To use multiple threads, run it with -j option as
			$ make -j4
	  	Note that, building the source may require up to 2-3 hours.
			 
	4)	After build is successful, launch emulator using command:
			$ emulator
	  	To see adb logs, use command:
			$ adb logcat
 




