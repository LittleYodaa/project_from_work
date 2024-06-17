package pl.programisci.task_crud.ftp;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.vfs2.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.programisci.task_crud.service.BookService;

import java.io.File;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@EnableScheduling
public class FtpFile {


//    private final static String REMOTE_INPUT_DIRECTORY = "/input";
//    private final static String REMOTE_ARCHIVE_DIRECTORY = "/archive";
//    private final static String REMOTE_FILE = "/book.txt";
//    private final static String LOCAL_FILE_PATH = "C:\\Users\\patryk.kawula\\IdeaProjects\\task_crud\\temporary";

    private final FtpConfiguration ftpConfiguration;
    private final FtpDirectory ftpDirectory;
    private final BookService bookService;

    public FtpFile(FtpConfiguration ftpConfiguration, FtpDirectory ftpDirectory, BookService bookService) {
        this.ftpConfiguration = ftpConfiguration;
        this.ftpDirectory = ftpDirectory;
        this.bookService = bookService;
    }

    @Scheduled(fixedRateString = "10", timeUnit = TimeUnit.SECONDS)
    private boolean checkFileExist() {
        FileObject fileObject = null;
        try {
            fileObject = ftpConfiguration.getRemoteFileObject(ftpConfiguration.getRemoteInputDirectory() + ftpConfiguration.getRemoteFile());
            log.info("Checking if file exists: " + fileObject.exists() + " " + fileObject.toString());
            if (!fileObject.exists()) {
                ftpDirectory.createDirectory("/input");
                ftpDirectory.createDirectory("/archive");
                log.info("File not exist : " + ftpConfiguration.getRemoteFile());
                return false;
            } else {
                downloadFile();
                moveFileToArchiveDirectory();
                bookService.fillTableWithBookFromFtpFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("File already exist : " + ftpConfiguration.getRemoteFile());
        return true;
    }

    public void downloadFile() {
        FileSystemManager fsManager = null;
        FileObject remoteFileToDownload = null;
        FileObject localFile = null;
        try {
            FileObject remoteFileObject = ftpConfiguration.getRemoteFileObject(ftpConfiguration.getRemoteInputDirectory() + ftpConfiguration.getRemoteFile());
            localFile = ftpConfiguration.getLocalFileObject(new File(ftpConfiguration.getLocalFilePath()).getAbsolutePath());
            localFile.copyFrom(remoteFileObject, Selectors.SELECT_SELF);
            log.info("Copy file from FTP to local directory");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ftpConfiguration.closeFileObject(remoteFileToDownload);
        }
        ftpConfiguration.closeFileObject(localFile);
    }

    public void moveFileToArchiveDirectory() {
        FileObject remoteFileToDownload = null;
        try {
            remoteFileToDownload = ftpConfiguration.getRemoteFileObject(ftpConfiguration.getRemoteInputDirectory() + ftpConfiguration.getRemoteFile());
            if (remoteFileToDownload.exists()) {
                remoteFileToDownload.moveTo(ftpConfiguration.getRemoteFileObject(ftpConfiguration.getRemoteArchiveDirectory() + ftpConfiguration.getRemoteFile()));
                log.info("Move file from /input to /archive directory on FTP server");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ftpConfiguration.closeFileObject(remoteFileToDownload);
        }
    }
}
