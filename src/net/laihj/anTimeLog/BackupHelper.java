package net.laihj.anTimeLog;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.lang.Exception;
import java.lang.Integer;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BackupHelper {
    public static final String DBName = "time_log";
    public static final String PackageName = "net.laihj.anTimeLog";
    public static final String BackupPath = "\\sdcard\\anTimeLog_bk";
    static private SimpleDateFormat mDateTimeFormat = new SimpleDateFormat("yyyyMMdd");
    public static boolean BackupDatabase () {

	try {
	    File sd = Environment.getExternalStorageDirectory();
	    File data = Environment.getDataDirectory();
	    if (sd.canWrite()) {
		String currentDBPath = "\\data\\" + PackageName + "\\databases\\" + DBName + "";
		String backupDBPath = "anTimeLog_bk\\" + getBackupFileName() ;
		File bPath = new File(BackupPath);
		if ( (!bPath.exists()) || (!bPath.isDirectory()) ) {
		    bPath.mkdirs();
		}
		File currentDB = new File(data, currentDBPath);
		File backupDB = new File(sd, backupDBPath);
		if (currentDB.exists()) {
		    FileChannel src = new FileInputStream(currentDB).getChannel();
		    FileChannel dst = new FileOutputStream(backupDB).getChannel();
		    dst.transferFrom(src, 0, src.size());
		    src.close();
		    dst.close();
		}
	    }
	} catch (Exception e) {
	    Log.i("backup",e.toString());
	    return false;
	}
	return true;
    }

    public static boolean restoreDatabase (String fileName) {
	if("".equals(fileName)) {
	    return false;
	}
	try {
	    File sd = Environment.getExternalStorageDirectory();
	    File data = Environment.getDataDirectory();
	    if (sd.canWrite()) {
		String currentDBPath = "\\data\\" + PackageName + "\\databases\\" + DBName + "";
		String backupDBPath = "anTimeLog_bk\\" + fileName;
		File bPath = new File(BackupPath);
		if ( (!bPath.exists()) || (!bPath.isDirectory()) ) {
		    bPath.mkdirs();
		}
		File currentDB = new File(data, currentDBPath);
		File backupDB = new File(sd, backupDBPath);
		if (currentDB.exists()) {
		    FileChannel src = new FileInputStream(backupDB).getChannel();
		    FileChannel dst = new FileOutputStream(currentDB).getChannel();
		    dst.transferFrom(src, 0, src.size());
		    src.close();
		    dst.close();
		}
	    }
	} catch (Exception e) {
	    Log.i("backup",e.toString());
	    return false;
	}
	return true;
    }

    public static String [] getBackupFileList() {
	ArrayList<String> files = new ArrayList<String> ();
	File sd = Environment.getExternalStorageDirectory();
	String backupPath = "anTimeLog_bk\\";
	File backupDBs = new File(sd,backupPath);
	for(File f:backupDBs.listFiles()) {
	    Log.i("backup",f.getName());
	    files.add(f.getName());
	}
	String [] res = new String[files.size()];
	return (String []) files.toArray(res);
    }
    public static String getBackupFileName()
    {
	Date theDate = new Date();
	String fileName = mDateTimeFormat.format(theDate);
	String [] files = getBackupFileList();
	int index = 0;
	int fileIndex = 0;
	for(String name:files) {
	    if(name.length() == 12) {
		if(name.substring(0,8).equals(fileName)) {
		    fileIndex = Integer.parseInt(name.substring(9,12));
		    if(fileIndex > index) {
			index = fileIndex;
		    }
		}
	    }
	}
	if(index + 1 <= 9) {
	    return fileName + "-00" + (index +1); 
	}
	if(index + 1 <= 99) {
	    return fileName + "-0" + (index+1);
	}
	return fileName + "-" + (index + 1) ;
    }
}