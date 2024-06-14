package pl.programisci.task_crud.ftp;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.vfs2.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;

@Slf4j
@Service
@EnableScheduling
public class FtpFile {
    private final static String REMOTE_INPUT_DIRECTORY = "/input";
    private final static String REMOTE_ARCHIVE_DIRECTORY = "/archive";
    private final static String REMOTE_FILE = "/book.txt";
    private final static String LOCAL_FILE_PATH = "C:\\Users\\patryk.kawula\\IdeaProjects\\task_crud\\temporary";

    private final FtpConfiguration ftpConfiguration;

    public FtpFile(FtpConfiguration ftpConfiguration) {
        this.ftpConfiguration = ftpConfiguration;
    }

    @Scheduled(cron = "0 * * * * *")
    private boolean checkFileExist() {
        FileObject fileObject = null;
        try {
            fileObject = ftpConfiguration.getRemoteFileObject(REMOTE_INPUT_DIRECTORY + REMOTE_FILE);
            if (!fileObject.exists()) {
                log.info("File not exist : " + REMOTE_FILE);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("File already exist : " + REMOTE_FILE);
        return true;
    }

    public void downloadFile() {
        FileSystemManager fsManager = null;
        FileObject remoteFileToDownload = null;
        FileObject localFile = null;
        try {
            FileObject remoteFileObject = ftpConfiguration.getRemoteFileObject(REMOTE_INPUT_DIRECTORY + REMOTE_FILE);
            localFile = ftpConfiguration.getLocalFileObject(new File(LOCAL_FILE_PATH).getAbsolutePath());
            localFile.copyFrom(remoteFileObject, Selectors.SELECT_SELF);
            log.info("Copy file from FTP to local directory");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ftpConfiguration.closeFileObject(remoteFileToDownload);
        }
        ftpConfiguration.closeFileObject(localFile);
    }

    public void moveFileToArchiveDirectroy() {
        FileObject remoteFileToDownload = null;
        try {
            remoteFileToDownload = ftpConfiguration.getRemoteFileObject(REMOTE_INPUT_DIRECTORY + REMOTE_FILE);
            if (remoteFileToDownload.exists()) {
                remoteFileToDownload.moveTo(ftpConfiguration.getRemoteFileObject(REMOTE_ARCHIVE_DIRECTORY + REMOTE_FILE));
                log.info("Move file from /input to /archive directory on FTP server");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ftpConfiguration.closeFileObject(remoteFileToDownload);
        }
    }
}
