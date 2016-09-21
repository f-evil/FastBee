package com.fyj.fastbee.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.fyj.easylinkingutils.utils.TimeUtils;
import com.fyj.easylinkingutils.utils.XLog;
import com.fyj.fastbee.globel.CachePath;
import com.fyj.fastbee.ui.activity.crash.CrashActivity;
import com.fyj.fastbee.ui.application.FastBebApplication;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * Created by fyj on 2015/12/9.
 */
public class CrashHandler {

	private static final String EXTRA_STACK_TRACE="EXTRA_STACK_TRACE";
	private static final String DEFAULT_HANDLER_PACKAGE_NAME = "com.android.internal.os";
	private static final int MAX_STACK_TRACE_SIZE = 131071;
	private String CAOC_HANDLER_PACKAGE_NAME ;
	private static Application application;
	private static WeakReference<Activity> lastActivityCreated = new WeakReference<>(null);
	private static boolean isInBackground = false;
	private static boolean launchErrorActivityWhenInBackground = true;
	private static Class<? extends Activity> errorActivityClass = null;

	private static final String TAG = "CrashHandler";
	private static final boolean DEBUG = true;

	private static final String FILE_NAME = "crash";
	private static final String FILE_NAME_SUFFIX = ".trace";

	private static CrashHandler sInstance = new CrashHandler();
	private Thread.UncaughtExceptionHandler mDefaultCrashHandler;
	private Context mContext;

	private CrashHandler() {
	}

	public static CrashHandler getInstance() {
		return sInstance;
	}

	public void init(Context context, String PACKAGE_NAME) {
		mContext = context.getApplicationContext();
		CAOC_HANDLER_PACKAGE_NAME=PACKAGE_NAME;
		install(context);
	}

	public void install(Context context) {
		try {
			if (context == null) {
				XLog.e(TAG, "Regist error.");
			} else {
				if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
					XLog.e(TAG, "Api level must > 14");
				}

				mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();

				if (mDefaultCrashHandler != null && mDefaultCrashHandler.getClass().getName().startsWith(CAOC_HANDLER_PACKAGE_NAME)) {
					XLog.e(TAG, "Regist too times");
				} else {
					if (mDefaultCrashHandler != null && !mDefaultCrashHandler.getClass().getName().startsWith
							(DEFAULT_HANDLER_PACKAGE_NAME)) {
						XLog.e(TAG, "Have same type Lib");
					}

					application = (Application) context.getApplicationContext();

					Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
						@Override
						public void uncaughtException(Thread thread, final Throwable throwable) {
							Log.e(TAG, "Crash,enter operate thread.", throwable);

							try {
								//导出异常信息到SD卡中
								dumpExceptionToSDCard(throwable);
								uploadExceptionToServer();
								//这里可以通过网络上传异常信息到服务器，便于开发人员分析日志从而解决bug
							} catch (IOException e) {
								e.printStackTrace();
							}

							errorActivityClass = guessErrorActivityClass(application);

							if (isStackTraceLikelyConflictive(throwable, errorActivityClass)) {
								Log.e(TAG, "Application destory.Can not restart.");
							} else {
								if (launchErrorActivityWhenInBackground || !isInBackground) {
									final Intent intent = new Intent(application, errorActivityClass);
									StringWriter sw = new StringWriter();
									PrintWriter pw = new PrintWriter(sw);
									throwable.printStackTrace(pw);
									String stackTraceString = sw.toString();

									if (stackTraceString.length() > MAX_STACK_TRACE_SIZE) {
										String disclaimer = "Stack is to long.";
										stackTraceString = stackTraceString.substring(0, MAX_STACK_TRACE_SIZE -
												disclaimer.length()) + disclaimer;
									}

									intent.putExtra(EXTRA_STACK_TRACE, stackTraceString);
//									intent.putExtra(EXTRA_RESTART_ACTIVITY_CLASS, restartActivityClass);
//									intent.putExtra(EXTRA_SHOW_ERROR_DETAILS, showErrorDetails);
									intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
									application.startActivity(intent);
								}
							}
							final Activity lastActivity = lastActivityCreated.get();
							if (lastActivity != null) {
								lastActivity.finish();
								lastActivityCreated.clear();
							}
							killCurrentProcess();
						}
					});
					if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
						application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
							int currentlyStartedActivities = 0;

							@Override
							public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
								if (activity.getClass() != errorActivityClass) {
									lastActivityCreated = new WeakReference<>(activity);
								}
							}

							@Override
							public void onActivityStarted(Activity activity) {
								currentlyStartedActivities++;
								isInBackground = (currentlyStartedActivities == 0);
							}

							@Override
							public void onActivityResumed(Activity activity) {
							}

							@Override
							public void onActivityPaused(Activity activity) {
							}

							@Override
							public void onActivityStopped(Activity activity) {
								currentlyStartedActivities--;
								isInBackground = (currentlyStartedActivities == 0);
							}

							@Override
							public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
							}

							@Override
							public void onActivityDestroyed(Activity activity) {
							}
						});
					}

					XLog.i(TAG, "Regist correct.");
				}
			}
		} catch (Throwable t) {
			XLog.e(TAG, "Regist error:" + t);
		}
	}

	private void dumpExceptionToSDCard(Throwable ex) throws IOException {
		//如果SD卡不存在或无法使用，则无法把异常信息写入SD卡
		if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			if (DEBUG) {
				XLog.e(TAG, "sdcard unmounted,skip dump exception");
				return;
			}
		}

		String path=CachePath.getImageCrashCachePath(mContext);

		String curTimeString = TimeUtils.getCurTimeString();
		File file = new File(path + FILE_NAME + curTimeString + FILE_NAME_SUFFIX);

		try {
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			pw.println(curTimeString);
			dumpPhoneInfo(pw);
			pw.println();
			ex.printStackTrace(pw);
			pw.close();
		} catch (Exception e) {
			XLog.e(TAG, "dump crash info failed");
		}
	}

	private void dumpPhoneInfo(PrintWriter pw) throws PackageManager.NameNotFoundException {
		PackageManager pm = mContext.getPackageManager();
		PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
		pw.print("App Version: ");
		pw.print(pi.versionName);
		pw.print('_');
		pw.println(pi.versionCode);

		//android版本号
		pw.print("OS Version: ");
		pw.print(Build.VERSION.RELEASE);
		pw.print("_");
		pw.println(Build.VERSION.SDK_INT);

		//手机制造商
		pw.print("Vendor: ");
		pw.println(Build.MANUFACTURER);

		//手机型号
		pw.print("Model: ");
		pw.println(Build.MODEL);

		//cpu架构
		pw.print("CPU ABI: ");
		pw.println(Build.CPU_ABI);
	}

	private void uploadExceptionToServer() {
	}

	private static Class<? extends Activity> guessErrorActivityClass(Context context) {
		Class<? extends Activity> resolvedActivityClass;

		resolvedActivityClass = getErrorActivityClassWithIntentFilter(context);

		if (resolvedActivityClass == null) {
			resolvedActivityClass = CrashActivity.class;
		}

		return resolvedActivityClass;
	}
	private static String INTENT_ACTION_ERROR_ACTIVITY = FastBebApplication.getAppContext().get().getPackageName()+".erroe";
	private static Class<? extends Activity> getErrorActivityClassWithIntentFilter(Context context) {
		List<ResolveInfo> resolveInfos = context.getPackageManager().queryIntentActivities(
				new Intent().setAction(INTENT_ACTION_ERROR_ACTIVITY),
				PackageManager.GET_RESOLVED_FILTER);

		if (resolveInfos != null && resolveInfos.size() > 0) {
			ResolveInfo resolveInfo = resolveInfos.get(0);
			try {
				return (Class<? extends Activity>) Class.forName(resolveInfo.activityInfo.name);
			} catch (ClassNotFoundException e) {
				Log.e(TAG, "get crash activity reference.",
						e);
			}
		}

		return null;
	}

	private static boolean isStackTraceLikelyConflictive(Throwable throwable, Class<? extends Activity>
			activityClass) {
		do {
			StackTraceElement[] stackTrace = throwable.getStackTrace();
			for (StackTraceElement element : stackTrace) {
				if ((element.getClassName().equals("android.app.ActivityThread") && element.getMethodName().equals
						("handleBindApplication")) || element.getClassName().equals(activityClass.getName())) {
					return true;
				}
			}
		} while ((throwable = throwable.getCause()) != null);
		return false;
	}

	private static void killCurrentProcess() {
		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(10);
	}
}


