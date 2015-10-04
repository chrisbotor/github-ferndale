package com.homeportal.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

//import package com.homeportal.service.ServiceException;

public class FileUtil {
	
	private static Logger logger = Logger.getLogger(FileUtil.class);
	
	
	public static void main(String[] args){
		
		
		//test to delete the file
		
		//FileUtil.delete(new File(completeFilename));
		//FileUtil.delete(new File("/apps/tomcat/webapps/cocoon/vishay/docs/moreinfo/racs-list.xml"));
		
		
		//test for getting xml content to be used in editing more info page
		String fileContent = FileUtil.getXmlContent("/apps/tomcat/webapps/cocoon/vishay/docs/moreinfo/abie-list.xml");
		System.out.println("CONTENT: \n" + fileContent);
		
		//FileUtil.getFileName("C:\\Users\\ranievas\\Desktop\\sample-excel.xlsx");
		//FileUtil.getSavedFilePath("/apps/searchoverride/excel/uploaded/sample-excel.xlsx");
		
		/*boolean copied = FileUtil.copyFile(new File("C:/apps/tomcat/webapps/admanagement/images/temp/banner-ads/background/banner-orange.gif"), 
				new File("C:/apps/tomcat/webapps/cocoon/vishay/resources/images/banner-ads/background/banner-orange.gif"));
		
		System.out.println("Copied: " + copied);
		
		if(copied){
			boolean deleted = FileUtil.delete(new File("C:/apps/tomcat/webapps/admanagement/images/temp/banner-ads/background/banner-orange.gif"));
			System.out.println("Deleted: " + deleted);
		}*/
		
		//System.out.println(FileUtil.renameImage("aake.gif", "ac000002"));
		
	}
	
	public static String getXmlContent(String path){
		String content = "";
	
		StringBuilder sb = new StringBuilder();
		String str = "";
		
		try {
			FileReader fr = new FileReader(path); 
			BufferedReader br = new BufferedReader(fr); 
			String s; 
			
		   while((s = br.readLine()) != null) {
		    	sb.append(s);
				//System.out.println(s); 
			} 
				fr.close(); 
				
				
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return sb.toString();
	}
	

	
	public static String getFileName(String absolutePath){
		String[] tokens = null;
		String token = "";
		String fileName = "";
		int tokensLength = 0;
		
		if(absolutePath != ""){
			tokens = absolutePath.split("\\\\");
			
			fileName = tokens[tokens.length-1];
		}
		
		//System.out.println("RACS File name: " + fileName);
		return fileName;
	}
	
	
	public static boolean copyFile(File source, File dest){
		boolean isCopied = false;
		InputStream in = null;
		OutputStream out = null;
		
		if(!dest.exists()){
			try{
				dest.createNewFile();
			}catch(IOException ex){
				logger.error("Cannot copy file, an error occured.", ex);
			}	
		}
		
		try{
			in = new FileInputStream(source);
			out = new FileOutputStream(dest);
			byte[] buf = new byte[1024];
			int len;

			while((len = in.read(buf)) > 0){
				out.write(buf, 0, len);
			}
				isCopied = true;
			
		}catch(IOException ex){
			logger.error("Cannot copy file, an error occured.", ex);
		}finally{
			try{
				in.close();
				out.close();
			}catch(IOException ex){
				logger.error("Cannot copy file, an error occured.", ex);
			}
		}

		return isCopied;
	}
	
	
	
	public static boolean delete(File resource){
		if(resource.isDirectory()){
			File[] childFiles = resource.listFiles();

			for(File child : childFiles){
				delete(child);
			}
		}

		System.out.println("DELETED? " + resource.delete());
		return resource.delete();
	}
	
	//public static String renameImage(String fileName, String id){
	public static String renameImage(String dir, String fileName, String id){
		String fName = "";
		//boolean isRenamed = false;
		File oldfile;
		File newfile;
		
		
		//oldfile = new File("/apps/tomcat/webapps/admanagement/images/temp/banner-ads/products/" + fileName);
		oldfile = new File(dir + fileName);
		
		if(fileName.toLowerCase().endsWith(".gif")){
			//newfile =new File("/apps/tomcat/webapps/admanagement/images/temp/banner-ads/products/" + id + ".gif");
			newfile =new File(dir + id + ".gif");

			if(oldfile.renameTo(newfile)){
				//isRenamed = true;
				fName = newfile.getName();
				System.out.println("Rename succesful");
			}else{
				System.out.println("FileUtil.java: Rename failed");
			}
		}else if(fileName.endsWith(".jpg")){
			//newfile =new File("/apps/tomcat/webapps/admanagement/images/temp/banner-ads/products/" + id + ".jpg");
			newfile =new File(dir + id + ".jpg");

			if(oldfile.renameTo(newfile)){
				//isRenamed = true;
				fName = newfile.getName();
				System.out.println("Rename succesful");
			}else{
				System.out.println("FileUtil.java: Rename failed");
			}
		}
		
		//return isRenamed;
		return fName;
	} 
	
	public static String getLastModified(File file){
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		String lastModified = "";
		
		if(file != null && file.isFile()){
			
			lastModified = sdf.format(file.lastModified());
		}
		
    	return lastModified;
	}
	
	public static FileInputStream getFileInputStream(String xlsPath){ //throws ServiceException{
		
		FileInputStream inputStream = null;
		
		try {
			inputStream = new FileInputStream(xlsPath);
			
		}catch (FileNotFoundException ex){
			//throw new ServiceException("File not found in the specified path.", ex);
		}
		
		return inputStream;	
	}
	
	
	/*public static String convertIntToString(int integerInput){
		
		String cellStringValue = "";
		
		try{
			int i = (int) integerInput;
			cellStringValue = String.valueOf(i);
		}catch(Exception ex){
			logger.debug("ERROR: encountered ", ex);
		}
		
		return cellStringValue;
	}*/
	
	public static String convertDoubleToString(double integerInput){
		String cellStringValue = "";
		
		try{
			int i = (int) integerInput;
			cellStringValue = String.valueOf(i);
		}catch(Exception ex){
			logger.debug("ERROR: encountered ", ex);
		}
		
		return cellStringValue;	
	}
	
	
	public static String getSavedFilePath(String path){
		System.out.println(path);
		return path;
	}
	
	//WRITE MORE INFO XML FILE
	public static boolean writeMiXmlFile(String completeFilename, String content){
		System.out.println("\nComplete Filename: " + completeFilename);
		
		try{
			FileWriter writer = new FileWriter(completeFilename);
			writer.write(content);
			
			writer.close();
			
		}catch(IOException ex){
			ex.printStackTrace();
		}
		
		
		return true;
		
	}
	
	
	public static String convertHtmlToXml(String data){
		System.out.println("converting... \n " + data);
		
		//replace the &nbsp; &deg; etc with it"s hexa equivalent example: &#160; or &#176;
		while(data.indexOf("&deg;") > -1 || 
				  data.indexOf("&nbsp;") > -1 || 
				  data.indexOf("@") > -1 || 
				  data.indexOf("?") > -1 ||
				  data.indexOf("&euro;") > -1 || 
				  data.indexOf("&lsquo;") > -1 || 
				  data.indexOf("&rsquo;") > -1 ||
				  data.indexOf("&ldquo;") > -1 ||
				  data.indexOf("&rdquo;") > -1 ||
				  data.indexOf("&ndash;") > -1 ||
				  data.indexOf("&mdash;") > -1 ||
				  data.indexOf("&iexcl;") > -1 ||
				  data.indexOf("&cent;") > -1 ||
				  data.indexOf("&pound;") > -1 ||
				  data.indexOf("&curren;") > -1 ||
				  data.indexOf("&yen;") > -1 ||
				  data.indexOf("&brvbar;") > -1 ||
				  data.indexOf("&sect;") > -1 ||
				  data.indexOf("&uml;") > -1 ||
				  data.indexOf("&copy;") > -1 ||
				  data.indexOf("&ordf;") > -1 ||
				  data.indexOf("&laquo;") > -1 ||
				  data.indexOf("&not;") > -1 ||
				  data.indexOf("&reg;") > -1 ||
				  data.indexOf("&macr;") > -1 ||
				  data.indexOf("&sup1;") > -1 ||
				  data.indexOf("&sup2;") > -1 ||
				  data.indexOf("&sup3;") > -1 ||
				  data.indexOf("&acute;") > -1 ||
				  data.indexOf("&micro;") > -1 ||
				  data.indexOf("&para;") > -1 ||
				  data.indexOf("&middot;") > -1 ||
				  data.indexOf("&cedil;") > -1 ||
				  data.indexOf("&ordm;") > -1 ||
				  data.indexOf("&raquo;") > -1 ||
				  data.indexOf("&frac14;") > -1 ||
				  data.indexOf("&frac12;") > -1 ||
				  data.indexOf("&frac34;") > -1 ||
				  data.indexOf("&iquest;") > -1 ||
				  data.indexOf("&Agrave;") > -1 ||
				  data.indexOf("&Aacute;") > -1 ||
				  data.indexOf("&Acirc;") > -1 ||
				  data.indexOf("&Atilde;") > -1 ||
				  data.indexOf("&Auml;") > -1 ||
				  data.indexOf("&Aring;") > -1 ||
				  data.indexOf("&AElig;") > -1 ||
				  data.indexOf("&Ccedil;") > -1 ||
				  data.indexOf("&Egrave;") > -1 ||
				  data.indexOf("&Egrave;") > -1 ||
				  data.indexOf("&Eacute;") > -1 ||
				  data.indexOf("&Ecirc;") > -1 ||
				  data.indexOf("&Euml;") > -1 ||
				  data.indexOf("&Igrave;") > -1 ||
				  data.indexOf("&Iacute;") > -1 ||
				  data.indexOf("&Icirc;") > -1 ||
				  data.indexOf("&Iuml;") > -1 ||
				  data.indexOf("&ETH;") > -1 ||
				  data.indexOf("&Ntilde;") > -1 ||
				  data.indexOf("&Ograve;") > -1 ||
				  data.indexOf("&Oacute;") > -1 ||
				  data.indexOf("&Ocirc;") > -1 ||
				  data.indexOf("&Otilde;") > -1 ||
				  data.indexOf("&Ouml;") > -1 ||
				  data.indexOf("&times;") > -1 ||
				  data.indexOf("&Oslash;") > -1 ||
				  data.indexOf("&Ugrave;") > -1 ||
				  data.indexOf("&Uacute;") > -1 ||
				  data.indexOf("&Ucirc;") > -1 ||
				  data.indexOf("&Uuml;") > -1 ||
				  data.indexOf("&Yacute;") > -1 ||
				  data.indexOf("&THORN;") > -1 ||
				  data.indexOf("&szlig;") > -1 ||
				  data.indexOf("&agrave;") > -1 ||
				  data.indexOf("&aacute;") > -1 ||
				  data.indexOf("&acirc;") > -1 ||
				  data.indexOf("&atilde;") > -1 ||
				  data.indexOf("&auml;") > -1 ||
				  data.indexOf("&aring;") > -1 ||
				  data.indexOf("&aelig;") > -1 ||
				  data.indexOf("&ccedil;") > -1 ||
				  data.indexOf("&egrave;") > -1 ||
				  data.indexOf("&eacute;") > -1 ||
				  data.indexOf("&ecirc;") > -1 ||
				  data.indexOf("&euml;") > -1 ||
				  data.indexOf("&igrave;") > -1 ||
				  data.indexOf("&iacute;") > -1 ||
				  data.indexOf("&icirc;") > -1 ||
				  data.indexOf("&iuml;") > -1 ||
				  data.indexOf("&eth;") > -1 ||
				  data.indexOf("&ntilde;") > -1 ||
				  data.indexOf("&ograve;") > -1 ||
				  data.indexOf("&oacute;") > -1 ||
				  data.indexOf("&ocirc;") > -1 ||
				  data.indexOf("&otilde;") > -1 ||
				  data.indexOf("&ouml;") > -1 ||
				  data.indexOf("&divide;") > -1 ||
				  data.indexOf("&oslash;") > -1 ||
				  data.indexOf("&ugrave;") > -1 ||
				  data.indexOf("&uacute;") > -1 ||
				  data.indexOf("&ucirc;") > -1 ||
				  data.indexOf("&uuml;") > -1 ||
				  data.indexOf("&yacute;") > -1 ||
				  data.indexOf("&thorn;") > -1 ||
				  data.indexOf("&yuml;") > -1 ||
				  data.indexOf("&oelig;") > -1 ||
				  data.indexOf("&OElig;") > -1 ||
				  data.indexOf("&sbquo;") > -1 ||
				  data.indexOf("&bdquo;") > -1 ||
				  data.indexOf("&hellip;") > -1 ||
				  data.indexOf("&trade;") > -1 ||
				  data.indexOf("&bull;") > -1 ||
				  
				  
				  data.indexOf("&rarr;") > -1 ||
				  data.indexOf("&rArr;") > -1 ||
				  data.indexOf("&hArr;") > -1 ||
				  data.indexOf("&diams;") > -1 ||
				  data.indexOf("&asymp;") > -1){
				
				
				//REPLACE
				data = data.replace("&sbquo;", "&#x201a;");
				data = data.replace("&bdquo;", "&#x201e;");
				
				data = data.replace("&rarr;", "&#x2192;");
				data = data.replace("&rArr;", "&#x21d2;");
				data = data.replace("&hArr;", "&#x21d4;");
				data = data.replace("&diams;", "&#x2666;");
				data = data.replace("&asymp;", "&#x2248;");
				
				data = data.replace("&deg;", "&#176;");
				data = data.replace("&nbsp;", "&#160;");
				data = data.replace("@", "&#64;");
				data = data.replace("?", "&#63;");
				data = data.replace("&euro;", "&#8364;");
				data = data.replace("&lsquo;", "&#8216;");
				data = data.replace("&rsquo;", "&#8217;");
				data = data.replace("&ldquo;", "&#8220;");
				data = data.replace("&rdquo;", "&#8221;");
				data = data.replace("&ndash;", "&#8211;");
				data = data.replace("&mdash;", "&#8212;");
				data = data.replace("&iexcl;", "&#161;");
				data = data.replace("&cent;", "&#162;");
				data = data.replace("&pound;", "&#163;");
				data = data.replace("&curren;", "&#164;");
				data = data.replace("&yen;", "&#165;");
				data = data.replace("&brvbar;", "&#166;");
				data = data.replace("&sect;", "&#167;");
				data = data.replace("&uml;", "&#168;");
				data = data.replace("&copy;", "&#169;");
				data = data.replace("&ordf;", "&#170;");
				data = data.replace("&laquo;", "&#171;");
				data = data.replace("&not;", "&#172;");
				data = data.replace("&reg;", "&#174;");
				data = data.replace("&macr;", "&#175;");
				data = data.replace("&sup1;", "&#185;");
				data = data.replace("&sup2;", "&#178;");
				data = data.replace("&sup3;", "&#179;");
				data = data.replace("&acute;", "&#180;");
				data = data.replace("&micro;", "&#181;");
				data = data.replace("&para;", "&#182;");
				data = data.replace("&middot;", "&#183;");
				data = data.replace("&cedil;", "&#184;");
				data = data.replace("&ordm;", "&#186;");
				data = data.replace("&raquo;", "&#187;");
				data = data.replace("&frac14;", "&#188;");
				data = data.replace("&frac12;", "&#189;");
				data = data.replace("&frac34;", "&#190;");
				data = data.replace("&iquest;", "&#191;");
				data = data.replace("&Agrave;", "&#192;");
				data = data.replace("&Aacute;", "&#193;");
				data = data.replace("&Acirc;", "&#194;");
				data = data.replace("&Atilde;", "&#195;");
				data = data.replace("&Auml;", "&#196;");
				data = data.replace("&Aring;", "&#197;");
				data = data.replace("&AElig;", "&#198;");
				data = data.replace("&Ccedil;", "&#199;");
				data = data.replace("&Egrave;", "&#200;");
				data = data.replace("&Eacute;", "&#201;");
				data = data.replace("&Ecirc;", "&#202;");
				data = data.replace("&Euml;", "&#203;");
				data = data.replace("&Igrave;", "&#204;");
				data = data.replace("&Iacute;", "&#205;");
				data = data.replace("&Icirc;", "&#206;");
				data = data.replace("&Iuml;", "&#207;");
				data = data.replace("&ETH;", "&#208;");
				data = data.replace("&Ntilde;", "&#209;");
				data = data.replace("&Ograve;", "&#210;");
				data = data.replace("&Oacute;", "&#211;");
				data = data.replace("&Ocirc;", "&#212;");
				data = data.replace("&Otilde;", "&#213;");
				data = data.replace("&Ouml;", "&#214;");
				data = data.replace("&times;", "&#215;");
				data = data.replace("&Oslash;", "&#216;");
				data = data.replace("&Ugrave;", "&#217;");
				data = data.replace("&Uacute;", "&#218;");
				data = data.replace("&Ucirc;", "&#219;");
				data = data.replace("&Uuml;", "&#220;");
				data = data.replace("&Yacute;", "&#221;");
				data = data.replace("&THORN;", "&#222;");
				data = data.replace("&szlig;", "&#223;");
				data = data.replace("&agrave;", "&#224;");
				data = data.replace("&aacute;", "&#225;");
				data = data.replace("&acirc;", "&#226;");
				data = data.replace("&atilde;", "&#227;");
				data = data.replace("&auml;", "&#228;");
				data = data.replace("&aring;", "&#229;");
				data = data.replace("&aelig;", "&#230;");
				data = data.replace("&ccedil;", "&#231;");
				data = data.replace("&egrave;", "&#232;");
				data = data.replace("&eacute;", "&#233;");
				data = data.replace("&ecirc;", "&#234;");
				data = data.replace("&euml;", "&#235;");
				data = data.replace("&igrave;", "&#236;");
				data = data.replace("&iacute;", "&#237;");
				data = data.replace("&icirc;", "&#238;");
				data = data.replace("&iuml;", "&#239;");
				data = data.replace("&eth;", "&#240;");
				data = data.replace("&ntilde;", "&#241;");
				data = data.replace("&ograve;", "&#242;");
				data = data.replace("&oacute;", "&#243;");
				data = data.replace("&ocirc;", "&#244;");
				data = data.replace("&otilde;", "&#245;");
				data = data.replace("&ouml;", "&#246;");
				data = data.replace("&divide;", "&#247;");
				data = data.replace("&oslash;", "&#248;");
				data = data.replace("&ugrave;", "&#249;");
				data = data.replace("&uacute;", "&#250;");
				data = data.replace("&ucirc;", "&#251;");
				data = data.replace("&uuml;", "&#252;");
				data = data.replace("&yacute;", "&#253;");
				data = data.replace("&thorn;", "&#254;");
				data = data.replace("&yuml;", "&#255;");
				data = data.replace("&sbquo;", "&#130;");
				data = data.replace("&bdquo;", "&#132;");
				data = data.replace("&OElig;", "&#338;");
				data = data.replace("&oelig;", "&#339;");
				data = data.replace("&hellip;", "&#x2026;");
				data = data.replace("&trade;", "&#x2122;");
				data = data.replace("&bull;", "&#x2022;");
			
			}
			System.out.println("FINAL DATA: " + data);

			return data;
		}

}
	







