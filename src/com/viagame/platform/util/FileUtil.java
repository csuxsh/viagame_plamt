package com.viagame.platform.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.content.Context;

public class FileUtil {
	
	public static boolean movingContextFile(Context context, String dst, String src) {
		try {
			InputStream is = context.getAssets().open(src);
			int size = is.available();
			if (size > 0) {
				File file = new File(dst);
				byte[] buffer = new byte[size];
				is.read(buffer);
				FileOutputStream os = new FileOutputStream(file);
				os.write(buffer);
				os.flush();
				os.close();
				os = null;
				file = null;
			} else {
				return false;
			} 
			is.close();
			is = null;
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

}
