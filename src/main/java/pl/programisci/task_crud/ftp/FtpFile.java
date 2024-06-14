package pl.programisci.task_crud.ftp;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.vfs2.*;
import org.apache.commons.vfs2.auth.StaticUserAuthenticator;
import org.apache.commons.vfs2.impl.DefaultFileSystemConfigBuilder;
import org.apache.commons.vfs2.provider.ftp.FtpFileSystemConfigBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;

@Slf4j
@Service
@EnableScheduling
public class FtpFile {

    String serverAddress = "ftp://localhost:21";
    String username = "admin";
    String password = "mypass";
    String remoteInputDir = "/input";
    String remoteArchiveDir = "/archive";
    String remoteFile = "/book.txt";
    String localFilePath = "C:\\Users\\patryk.kawula\\IdeaProjects\\task_crud\\temporary";


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


            fileObject = fsManager.resolveFile(serverAddress + remoteInputDir + remoteFile, opts);

            log.info("Typ pliku: " + fileObject.getType());

            if (!fileObject.exists()) {
                log.info("Plik nie istnieje : " + remoteFile);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("Plik istnieje : " + remoteFile);
        return true;
    }

    public void downloadFile() {
        FileSystemManager fsManager = null;
        FileObject remoteFileToDownload = null;
        FileObject localFile = null;
        FileSystemOptions opts = new FileSystemOptions();
        PrintWriter printWriter = null;
        OutputStream outputStream = null;

        try {
            fsManager = VFS.getManager();

            StaticUserAuthenticator auth = new StaticUserAuthenticator(null, username, password);
            DefaultFileSystemConfigBuilder.getInstance().setUserAuthenticator(opts, auth);
            FtpFileSystemConfigBuilder.getInstance().setPassiveMode(opts, true);

            remoteFileToDownload = fsManager.resolveFile(serverAddress + remoteInputDir + remoteFile, opts);
//            outputStream = remoteFileToDownload.getContent().getOutputStream(true);
//            printWriter = new PrintWriter(outputStream);

            localFile = fsManager.resolveFile(new File(localFilePath).getAbsolutePath());
            localFile.copyFrom(remoteFileToDownload, Selectors.SELECT_SELF);
            log.info("Copy file from FTP to local directory");

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

    public void moveFileToArchiveDirectroy() {
        FileSystemManager fsManager = null;
        FileObject remoteFileToDownload = null;
        FileObject localFile = null;
        FileSystemOptions opts = new FileSystemOptions();

        try {
            fsManager = VFS.getManager();

            StaticUserAuthenticator auth = new StaticUserAuthenticator(null, username, password);
            DefaultFileSystemConfigBuilder.getInstance().setUserAuthenticator(opts, auth);
            FtpFileSystemConfigBuilder.getInstance().setPassiveMode(opts, true);


            remoteFileToDownload = fsManager.resolveFile(serverAddress + remoteInputDir + remoteFile, opts);

            if (remoteFileToDownload.exists()) {
                remoteFileToDownload.moveTo(fsManager.resolveFile(serverAddress + remoteArchiveDir + remoteFile, opts));
                log.info("Move file from /input to /archive directory on FTP server");
            }
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
