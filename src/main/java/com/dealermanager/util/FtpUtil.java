package com.dealermanager.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.*;

import java.io.*;

@Slf4j
public class FtpUtil {


    static String charset = "GBK";

    /**
     * Description: 向FTP服务器上传文件
     *
     * @param host     FTP服务器hostname
     * @param port     FTP服务器端口
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @param basePath FTP服务器基础目录
     * @param filePath FTP服务器文件存放路径。文件的路径为basePath+filePath
     * @param filename 上传到FTP服务器上的文件名
     * @param input    输入流
     * @return 成功返回true，否则返回false
     */
    public static boolean uploadFile(String host, int port, String username, String password, String basePath,
                                     String filePath, String filename, InputStream input) {
        boolean result = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(host, port);// 连接FTP服务器
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            ftp.login(username, password);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                log.error("FTP连接异常," + ftp.getReplyString());
                return false;
            }
            if(FTPReply.isPositiveCompletion(ftp.sendCommand("OPTS UTF8","ON"))){
                charset = "UTF-8";
            }
            ftp.setControlEncoding(charset);
            ftp.setBufferSize(1024);
            ftp.enterLocalPassiveMode();
            ftp.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
            //切换到上传目录
            if (!ftp.changeWorkingDirectory( filePath)) {
                //如果目录不存在创建目录
                String[] dirs = filePath.split("/");
                String tempPath = "";
                for (String dir : dirs) {
                    if (null == dir || "".equals(dir)) continue;
                    tempPath = dir;
                    if (!ftp.changeWorkingDirectory(tempPath)) {  //进不去目录，说明该目录不存在
                        if (!ftp.makeDirectory(tempPath)) { //创建目录
                            //如果创建文件目录失败，则返回
                            log.error("创建文件目录" + tempPath + "失败." + ftp.getReplyString() );
                            return false;
                        } else {
                            //目录存在，则直接进入该目录
                            ftp.changeWorkingDirectory(tempPath);
                        }
                    }
                }
            }
            //设置上传文件的类型为二进制类型
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            String workPath = ftp.printWorkingDirectory();
            log.info("workPath : " +workPath);
            //上传文件
            //需要对文件名进行转码
            String remote = new String(filename.getBytes(charset),"iso-8859-1");
            if (!ftp.storeFile(remote, input)) {
                log.error("文件上传失败," + ftp.getReplyString());
                return false;
            }
            input.close();
            ftp.logout();
            result = true;
        } catch (IOException e) {
            log.error("文件上传失败",e);
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return result;
    }

    /**
     * Description: 从FTP服务器下载文件
     *
     * @param host       FTP服务器hostname
     * @param port       FTP服务器端口
     * @param username   FTP登录账号
     * @param password   FTP登录密码
     * @param remotePath FTP服务器上的相对路径
     * @param fileName   要下载的文件名
     * @param localPath  下载后保存到本地的路径
     * @return
     */
    public static boolean downloadFile(String host, int port, String username, String password, String remotePath,
                                       String fileName, String localPath) {
        boolean result = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(host, port);
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            ftp.login(username, password);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                log.error("FTP连接异常," + ftp.getReplyString());
                ftp.disconnect();
                return false;
            }
            ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
            FTPFile[] fs = ftp.listFiles(); //文件名是iso编码
            String encodeFilename = new String(fileName.getBytes("GBK"),"iso-8859-1");

            for (FTPFile ff : fs) {
                if (ff.getName().equals(encodeFilename)) {
                    File localFile = new File(localPath + "/" + fileName);

                    OutputStream is = new FileOutputStream(localFile);
                    ftp.retrieveFile(ff.getName(), is);
                    is.close();
                }
            }
            ftp.logout();
            result = true;
        } catch (IOException e) {
            log.error("FTP连接异常:" + e);
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return result;
    }
}
    