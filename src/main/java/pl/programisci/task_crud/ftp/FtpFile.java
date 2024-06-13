package pl.programisci.task_crud.ftp;

import org.apache.commons.vfs2.*;
import org.apache.commons.vfs2.auth.StaticUserAuthenticator;
import org.apache.commons.vfs2.impl.DefaultFileSystemConfigBuilder;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;
import org.apache.commons.vfs2.provider.ftp.FtpFileSystemConfigBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;

@Service
@EnableScheduling
public class FtpFile {

    String serverAddress = "ftp://localhost:21";
    String username = "admin";
    String password = "mypass";
    String remoteDir = "/input";
    String remoteFile = "/book.txt";
    String localFilePath = "C:/Users/patryk.kawula/IdeaProjects/task/";


    @Scheduled(cron = "0 * * * * *")
    private boolean checkFileExist() {
        FileSystemManager fsManager = null;
        FileObject fileObject = null;
        FileSystemOptions opts = new FileSystemOptions();

        try {
            fsManager = VFS.getManager();

            StaticUserAuthenticator auth = new StaticUserAuthenticator(null, username, password);
            DefaultFileSystemConfigBuilder.getInstance().setUserAuthenticator(opts, auth);
            FtpFileSystemConfigBuilder.getInstance().setPassiveMode(opts, true);


            fileObject = fsManager.resolveFile(serverAddress + remoteDir + remoteFile, opts);

            System.out.println("Typ pliku: " + fileObject.getType());

            if (!fileObject.exists()) {
                System.out.println("Plik nie istnieje : " + remoteFile);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Plik istnieje : " + remoteFile);
        return true;
    }

    public void downloadFile() {
        FileSystemManager fsManager = null;
        FileObject remoteFileToDownload = null;
        FileObject localFile = null;
        FileSystemOptions opts = new FileSystemOptions();

        try {
            fsManager = VFS.getManager();

            StaticUserAuthenticator auth = new StaticUserAuthenticator(null, username, password);
            DefaultFileSystemConfigBuilder.getInstance().setUserAuthenticator(opts, auth);
            FtpFileSystemConfigBuilder.getInstance().setPassiveMode(opts, true);


            remoteFileToDownload = fsManager.resolveFile(serverAddress + remoteDir + remoteFile, opts);
            localFile = fsManager.resolveFile(new File(localFilePath).getAbsolutePath());


//            localFile.copyFrom(remoteFileToDownload, Selectors.SELECT_SELF);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (remoteFileToDownload != null) {
                try {
                    remoteFileToDownload.close();
                } catch (FileSystemException e) {
                    e.printStackTrace();
                }
            }
            if (localFile != null) {
                try {
                    localFile.close();
                } catch (FileSystemException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
