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

    private final FtpConfiguration ftpConfiguration;
    private final FtpDirectory ftpDirectory;
    private final BookService bookService;

    public FtpFile(FtpConfiguration ftpConfiguration, FtpDirectory ftpDirectory, BookService bookService) {
        this.ftpConfiguration = ftpConfiguration;
        this.ftpDirectory = ftpDirectory;
        this.bookService = bookService;
    }

    @Scheduled(fixedRateString = "10", timeUnit = TimeUnit.SECONDS)
    private void checkFileExist() throws FileSystemException {
        FileObject fileObject = null;
        try {
            fileObject = ftpConfiguration.getRemoteFileObject(ftpConfiguration.getRemoteInputDirectory() + ftpConfiguration.getRemoteFile());
            fileObject.refresh();
            log.info("Checking if file exists: " + fileObject.exists() + " " + fileObject.toString());
            if (fileObject.exists()) {
                ftpDirectory.createDirectory("/input");
                ftpDirectory.createDirectory("/archive");
                downloadFile();
                moveFileToArchiveDirectory();
                bookService.fillTableWithBookFromFtpFile();
                log.info("File exist : " + ftpConfiguration.getRemoteFile());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            FileSystem fileSystem = fileObject.getFileSystem();
            VFS.getManager().closeFileSystem(fileSystem);
        }
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
