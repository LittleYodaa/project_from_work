package pl.programisci.task_crud.ftp;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.vfs2.FileObject;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FtpDirectory {
    private final FtpConfiguration ftpConfiguration;

    public FtpDirectory(FtpConfiguration ftpConfiguration) {
        this.ftpConfiguration = ftpConfiguration;
    }

    public void createDirectory(String directory) {
        FileObject remoteDirObject = null;
        try {
            remoteDirObject = ftpConfiguration.getRemoteFileObject(directory);
            log.info("Creating directory: " + remoteDirObject.toString());

            log.info("File type: " + remoteDirObject.getType());

            if (!remoteDirObject.exists()) {
                remoteDirObject.createFolder();
                log.info("Directory created: " + directory);
            } else {
                log.info("Directory already exist: " + directory);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ftpConfiguration.closeFileObject(remoteDirObject);
        }
    }
}