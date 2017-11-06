package com.xiangshangban.transit_service.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.web.multipart.MultipartFile;

public class UploadFile {
	public static void upload(MultipartFile oldfile){
		try {
			
		
		if(!oldfile.isEmpty()){
			InputStream in = oldfile.getInputStream();
			String name = oldfile.getOriginalFilename();
			System.out.println(name+"\t"+oldfile.getSize()+"\t"+in.available());
			byte [] b = new byte[in.available()];
			in.read(b);
			File targetFile = new File("C:/Users/cachee/Desktop/aaa/"+name);
			OutputStream out = new FileOutputStream(targetFile);
			out.write(b);
			out.flush();
			out.close();
			in.close();
		}
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	}
}
