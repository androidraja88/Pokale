package com.ganesh.memes.crop;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

import android.graphics.Bitmap;
import android.os.Environment;

public final class FileUtil
{

	public static final String NEWLINE = System.getProperty("line.separator");
	public static final String APPROOT = "UMMoka";
	public static final String ASSERT_PATH="file:///android_asset";
	public static final String RES_PATH="file:///android_res";
	
	public static final String CACHE_IMAGE_SUFFIX=File.separator + APPROOT+ File.separator + "images" + File.separator;
	public static final String CACHE_VOICE_SUFFIX=File.separator + APPROOT+ File.separator + "voice" + File.separator;
	public static final String CACHE_MATERIAL_SUFFIX=File.separator + APPROOT + File.separator + "material" + File.separator;
	public static final String LOG_SUFFIX=File.separator + APPROOT + File.separator + "Log" + File.separator;

	public static String SDCARD_PAHT ;
	public static String CURRENT_PATH = "";
	
	static
	{
		init();
	}

	public static void init()
	{
		SDCARD_PAHT = Environment.getExternalStorageDirectory().getPath();
		
		if(android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
		{
			CURRENT_PATH = SDCARD_PAHT;
		} 
	}


	public static String getDiffPath()
	{
	
		return SDCARD_PAHT;
	}
	
	
	public static String getDiffPath(String pathIn)
	{
		return pathIn.replace(CURRENT_PATH, getDiffPath());
	}

	
	public static boolean writeFile(String destFilePath, byte[] data, int startPos, int length)
	{
		try
		{
			if (!createFile(destFilePath))
			{
				return false;
			}
			FileOutputStream fos = new FileOutputStream(destFilePath);
			fos.write(data, startPos, length);
			fos.flush();
			if (null != fos)
			{
				fos.close();
				fos = null;
			}
			return true;

		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	
	public static boolean writeFile(String destFilePath, InputStream in)
	{
		try
		{
			if (!createFile(destFilePath))
			{
				return false;
			}
			FileOutputStream fos = new FileOutputStream(destFilePath);
			int readCount = 0;
			int len = 1024;
			byte[] buffer = new byte[len];
			while ((readCount = in.read(buffer)) != -1)
			{
				fos.write(buffer, 0, readCount);
			}
			fos.flush();
			if (null != fos)
			{
				fos.close();
				fos = null;
			}
			if (null != in)
			{
				in.close();
				in = null;
			}
			return true;
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return false;
	}
	
	public static boolean appendFile(String filename,byte[]data,int datapos,int datalength)
	{
		try {
			
			createFile(filename);
			
			RandomAccessFile rf= new RandomAccessFile(filename, "rw");
			rf.seek(rf.length());
			rf.write(data, datapos, datalength);
			if(rf!=null)
			{
				rf.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}


	public static byte[] readFile(String filePath)
	{
		try
		{
			if (isFileExist(filePath))
			{
				FileInputStream fi = new FileInputStream(filePath);
				return readInputStream(fi);
			}
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] readInputStream(InputStream in)
	{
		try
		{
			ByteArrayOutputStream os = new ByteArrayOutputStream();

			byte[] b = new byte[in.available()];
			int length = 0;
			while ((length = in.read(b)) != -1)
			{
				os.write(b, 0, length);
			}

			b = os.toByteArray();

			in.close();
			in = null;

			os.close();
			os = null;

			return b;

		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] readNetWorkInputStream(InputStream in)
	{
		ByteArrayOutputStream os=null;
		try
		{
			os = new ByteArrayOutputStream();
			
			int readCount = 0;
			int len = 1024;
			byte[] buffer = new byte[len];
			while ((readCount = in.read(buffer)) != -1)
			{
				os.write(buffer, 0, readCount);
			}

			in.close();
			in = null;

			return os.toByteArray();

		} catch (IOException e)
		{
			e.printStackTrace();
		}finally{
			if(null!=os)
			{
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				os = null;
			}
		}
		return null;
	}


	public static boolean copyFiles(String sourceFile, String destFile,boolean shouldOverlay)
	{
		try
		{
			if(shouldOverlay)
			{
				deleteFile(destFile);
			}
			FileInputStream fi = new FileInputStream(sourceFile);
			writeFile(destFile, fi);
			return true;
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return false;
	}


	public static boolean isFileExist(String filePath)
	{
		File file = new File(filePath);
		return file.exists();
	}


	public static boolean createFile(String filePath)
	{
		try
		{
			File file = new File(filePath);
			if (!file.exists())
			{
				if (!file.getParentFile().exists())
				{
					file.getParentFile().mkdirs();
				}

				return file.createNewFile();
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return true;
	}

	public static boolean deleteFile(String filePath)
	{
		try {
			File file = new File(filePath);
			if (file.exists())
			{
				return file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void deleteDirectory(File dir)
	{
		if (dir.isDirectory())  
	    {  
	        File[] listFiles = dir.listFiles();  
	        for (int i = 0; i < listFiles.length ; i++)  
	        {  
	        	deleteDirectory(listFiles[i]);
	        }  
	    }
	    dir.delete();  
	}


	public static InputStream String2InputStream(String str)
	{
		ByteArrayInputStream stream = new ByteArrayInputStream(str.getBytes());
		return stream;
	}


	public static String inputStream2String(InputStream is)
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		StringBuffer buffer = new StringBuffer();
		String line = "";

		try
		{
			while ((line = in.readLine()) != null)
			{
				buffer.append(line);
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return buffer.toString();
	}
	
	public static void reNameSuffix(File dir,String oldSuffix,String newSuffix)
	{
		if (dir.isDirectory())  
	    {  
	        File[] listFiles = dir.listFiles();  
	        for (int i = 0; i < listFiles.length ; i++)  
	        {  
	        	reNameSuffix(listFiles[i],oldSuffix,newSuffix);
	        }  
	    }
		else
		{
			dir.renameTo(new File(dir.getPath().replace(oldSuffix, newSuffix)));
		}
	}
	
	public static void writeImage(Bitmap bitmap,String destPath,int quality)
	{
		try {
			FileUtil.deleteFile(destPath);
			if (FileUtil.createFile(destPath))
			{
				FileOutputStream out = new FileOutputStream(destPath);
				if (bitmap.compress(Bitmap.CompressFormat.JPEG,quality, out))
				{
					out.flush();
					out.close();
					out = null;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
