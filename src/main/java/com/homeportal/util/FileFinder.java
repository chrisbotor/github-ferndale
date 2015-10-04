package com.homeportal.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

public class FileFinder {
	
	/*public static void main(String[] args){
		List<String> filenamesList = new ArrayList<String>();
		//String imageDir = "/Racs/Programming/Docs/Projects/Banner-ads/sample";
		String imageDir = "/Racs/Programming/Codes/workspace2/admanagement/src/main/webapp/images/copy/banner-ads/background";
		filenamesList = FileFinder.findImageFilenames(imageDir);
		
		for(String filename:filenamesList){
			System.out.println("My filename: " + filename);
		}
	}*/


	public static List<String> findImageFilenames(String dirName){
		List<String> filenames = new ArrayList<String>();
		File dir = new File(dirName);
		String filename = "";
		
		File[] fileList = dir.listFiles(new FilenameFilter() { 
            public boolean accept(File dir, String filename)
                 {return (filename.endsWith(".gif") || filename.endsWith(".jpg") || filename.endsWith(".xml"));}
		});
		
		if(fileList.length > 0){
			System.out.println("\nin FileFinder.java");
			System.out.println("Current files in this directory (" + dirName + ")");
			for(int i=0; i<fileList.length; i++){
				filename = FileUtil.getFileName(fileList[i].getName());
				filenames.add(filename);
				System.out.println("Filename: " + filename);
			}
		}
		
		return filenames;
	}
	
}


//find images
/*public static File[] findImageFiles(String dirName){
	File dir = new File(dirName);

	return dir.listFiles(new FilenameFilter() { 
                 public boolean accept(File dir, String filename)
                      {return (filename.endsWith(".gif") || filename.endsWith(".jpg"));}
    });
 }*/