package com.letv.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileUtil {
	private static final Log log = LogFactory.getLog(FileUtil.class);
	
	
	/** 
     * 遍历文件夹中文件 
     *  
     * @param filepath 
     * @return 返回file［］数组 
     * 只能遍历一级目录！！！
     */  
    public static File[] getFileList(String filepath) {  
        File d = null;  
        File list[] = null;  
        // 建立当前目录中文件的File对象  
        try {  
            d = new File(filepath);  
            if (d.exists()) {  
                list = d.listFiles();  
            }  
        } catch (Exception e) {  
        	System.out.println("遍历目录操作出错");
        	log.error("遍历目录操作出错："+e.getMessage());  
        }  
        // 取得代表目录中所有文件的File对象数组  
  
        return list;  
    }  
  
    /** 
     * 读取文本文件内容 
     *  
     * @param filePathAndName 
     *            带有完整绝对路径的文件名 
     * @param encoding 
     *            文本文件打开的编码方式 
     * @return 返回文本文件的内容 
     */  
    public static String readTxt(String filePathAndName, String encoding)  
            throws IOException {  
        encoding = encoding.trim();  
        StringBuffer str = new StringBuffer("");  
        String st = "";  
        try {  
            FileInputStream fs = new FileInputStream(filePathAndName);  
            InputStreamReader isr;  
            if (encoding.equals("")) {  
                isr = new InputStreamReader(fs);  
            } else {  
                isr = new InputStreamReader(fs, encoding);  
            }  
            BufferedReader br = new BufferedReader(isr);  
            try {  
                String data = "";  
                while ((data = br.readLine()) != null) {  
                    str.append(data);  
                }  
            } catch (Exception e) {  
                str.append(e.toString());  
            }  
            st = str.toString();  
            if (st != null && st.length() > 1)  
                st = st.substring(0, st.length() - 1);  
        } catch (IOException es) {  
            st = "";  
        }  
        return st;  
    }  
  
    /** 
     * 新建目录 
     *  
     * @param folderPath 
     *            目录 
     * @return 返回目录创建后的路径 
     * 只能创建一级目录,如果上级目录不存在,无法创建
     */  
    public static String createFolder(String folderPath) {  
        String txt = folderPath;  
        try {  
            java.io.File myFilePath = new java.io.File(txt);  
            txt = folderPath;  
            if (!myFilePath.exists()) {  
                myFilePath.mkdir();  
            }  
        } catch (Exception e) {  
            System.out.println("创建目录操作出错");
        	log.error("创建目录操作出错："+e.getMessage());
        }  
        return txt;  
    }  
  
    /** 
     * 多级目录创建 
     *  
     * @param folderPath 
     *            准备要在本级目录下创建新目录的目录路径例如 c:myf 
     * @param paths 
     *            无限级目录参数，各级目录以单数线区分 例如 a|b|c 
     * @return 返回创建文件后的路径 
     */  
    public static String createFolders(String folderPath, String paths) {  
        String txts = folderPath;  
        try {  
            String txt;  
            txts = folderPath;  
            StringTokenizer st = new StringTokenizer(paths, "|");  
            for (int i = 0; st.hasMoreTokens(); i++) {  
                txt = st.nextToken().trim();  
                 txts = createFolder(txts + txt + "/");  
                  
            }  
        } catch (Exception e) {  
        	System.out.println("创建文件夹出错");
        	log.error("创建文件夹出错："+e.getMessage());   
        }  
        return txts;  
    }
	
	/** 
     * 新建文件 
     *  
     * @param filePathAndName 
     *            文本文件完整绝对路径及文件名 
     * @param fileContent 
     *            文本文件内容 
     * @return 
     */  
    public static void createFile(String filePathAndName, String fileContent) {  
    	
        try {  
            String filePath = filePathAndName;  
            File myFilePath = new File(filePath);  
            if (!myFilePath.exists()) {  
                myFilePath.createNewFile();  
            }  
            FileWriter resultFile = new FileWriter(myFilePath);  
            PrintWriter myFile = new PrintWriter(resultFile);  
            String strContent = fileContent;  
            myFile.println(strContent);  
            myFile.close();  
            resultFile.close();  
        } catch (Exception e) {
        	System.out.println("新建文件操作出错");
        	log.error("创建文件操作出错："+e.getMessage());  
        }  
    }
    
    /** 
     * 有编码方式的文件创建 
     *  
     * @param filePathAndName 
     *            文本文件完整绝对路径及文件名 
     * @param fileContent 
     *            文本文件内容 
     * @param encoding 
     *            编码方式 例如 GBK 或者 UTF-8 
     * @return 
     */  
    public static void createFile(String filePathAndName, String fileContent,  
            String encoding) {  
  
        try {  
            String filePath = filePathAndName;  
            File myFilePath = new File(filePath);  
            if (!myFilePath.exists()) {  
                myFilePath.createNewFile();  
            }  
            PrintWriter myFile = new PrintWriter(myFilePath, encoding);  
            String strContent = fileContent;  
            myFile.println(strContent);  
            myFile.close();  
        } catch (Exception e) {  
        	System.out.println("新建文件操作出错");
        	log.error("创建文件操作出错:"+e.getMessage());    
        }  
    }
    
    /** 
     * 删除文件 
     *  
     * @param filePathAndName 
     *            文本文件完整绝对路径及文件名 
     * @return Boolean 成功删除返回true遭遇异常返回false 
     */  
    public static boolean delFile(String filePathAndName) {  
        boolean bea = false;  
        try {  
            String filePath = filePathAndName;  
            File myDelFile = new File(filePath);  
            if (myDelFile.exists()) {  
                myDelFile.delete();  
                bea = true;  
            } else {  
                bea = false;  
                System.out.println("删除文件,文件不存在.");
            	log.error("删除文件,文件不存在."); 
            }  
        } catch (Exception e) {  
        	System.out.println(e.getMessage());
        	log.error(e.getMessage());  
        }  
        return bea;  
    }
    
    /** 
     * 删除整个文件夹,包括文件夹中的文件夹,以及所有文件.
     *  
     * @param folderPath 
     *            文件夹完整绝对路径 
     * @return 
     */  
    public static void delFolder(String folderPath) {  
        try {  
            delAllFile(folderPath); // 删除完里面所有内容  
            String filePath = folderPath;  
            filePath = filePath.toString();  
            java.io.File myFilePath = new java.io.File(filePath);  
            myFilePath.delete(); // 删除空文件夹  
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        	log.error(e.getMessage());  
        }  
    }
    
    /** 
     * 删除指定文件夹下所有文件和文件夹.
     *  
     * @param path 
     *            文件夹完整绝对路径 
     * @return 
     * @return 
     */  
    public static boolean delAllFile(String path) {  
        boolean bea = false;  
        File file = new File(path);  
        if (!file.exists()) {  
            return bea;  
        }  
        if (!file.isDirectory()) {  
            return bea;  
        }  
        String[] tempList = file.list();  
        File temp = null;  
        for (int i = 0; i < tempList.length; i++) {  
            if (path.endsWith(File.separator)) {  
                temp = new File(path + tempList[i]);  
            } else {  
                temp = new File(path + File.separator + tempList[i]);  
            }  
            if (temp.isFile()) {  
                temp.delete();  
            }  
            if (temp.isDirectory()) {  
                delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件  
                delFolder(path + "/" + tempList[i]);// 再删除空文件  
                bea = true;  
            }  
        }  
        return bea;  
    }
    
    
    /** 
     * 复制单个文件 
     *  
     * @param oldPathFile 
     *            准备复制的文件源 
     * @param newPathFile 
     *            拷贝到新绝对路径带文件名 
     * @return 
     */  
    public void copyFile(String oldPathFile, String newPathFile) {  
        try {  
            int bytesum = 0;  
            int byteread = 0;  
            File oldfile = new File(oldPathFile);  
            if (oldfile.exists()) { // 文件存在  
                InputStream inStream = new FileInputStream(oldPathFile); // 读入源文件  
                FileOutputStream fs = new FileOutputStream(newPathFile);  
                byte[] buffer = new byte[1444];  
                while ((byteread = inStream.read(buffer)) != -1) {  
                    bytesum += byteread; // 字节 文件大小  
                    System.out.println(bytesum);  
                    fs.write(buffer, 0, byteread);  
                }  
                inStream.close();  
            }  
        } catch (Exception e) {  
            System.out.println(e.getMessage());
        	log.error("复制单个文件操作出错:"+e.getMessage());  
        }  
    }  
  
    /** 
     * 复制整个文件夹的内容 
     *  
     * @param oldPath 
     *            准备拷贝的目录 
     * @param newPath 
     *            指定绝对路径的新目录 
     * @return 
     */  
    public void copyFolder(String oldPath, String newPath) {  
        try {  
            new File(newPath).mkdirs(); // 如果文件夹不存在 则建立新文件  
            File a = new File(oldPath);  
            String[] file = a.list();  
            File temp = null;  
            for (int i = 0; i < file.length; i++) {  
                if (oldPath.endsWith(File.separator)) {  
                    temp = new File(oldPath + file[i]);  
                } else {  
                    temp = new File(oldPath + File.separator + file[i]);  
                }  
                if (temp.isFile()) {  
                    FileInputStream input = new FileInputStream(temp);  
                    FileOutputStream output = new FileOutputStream(newPath  
                            + "/" + (temp.getName()).toString());  
                    byte[] b = new byte[1024 * 5];  
                    int len;  
                    while ((len = input.read(b)) != -1) {  
                        output.write(b, 0, len);  
                    }  
                    output.flush();  
                    output.close();  
                    input.close();  
                }  
                if (temp.isDirectory()) {// 如果是子文件  
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);  
                }  
            }  
        } catch (Exception e) {  
        	System.out.println(e.getMessage());
        	log.error("复制整个文件夹内容操作出错:"+e.getMessage());  
        }  
    }  
  
    /** 
     * 移动文件 
     *  
     * @param oldPath 
     * @param newPath 
     * @return 
     */  
    public void moveFile(String oldPath, String newPath) {  
        copyFile(oldPath, newPath);  
        delFile(oldPath);  
    }  
  
    /** 
     * 移动目录 
     *  
     * @param oldPath 
     * @param newPath 
     * @return 
     */  
    public void moveFolder(String oldPath, String newPath) {  
        copyFolder(oldPath, newPath);  
        delFolder(oldPath);  
    } 
    
    /**  
     * 判断一个文件或者文件夹是否存在  
     *  
     * @param filePath 文件路径  
     * @return 存在返回true，否则返回false  
     */  
    public static boolean isExist(String filePath) {  
            return new File(filePath).exists(); 
    }
    
    
	
}
