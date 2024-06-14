package pl.programisci.task_crud.ftp;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.auth.StaticUserAuthenticator;
import org.apache.commons.vfs2.impl.DefaultFileSystemConfigBuilder;
import org.apache.commons.vfs2.provider.ftp.FtpFileSystemConfigBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FtpDirectory {
    public void createDirectory(String directory) {
        String serverAddress = "ftp://localhost:21";
        String username = "admin";
        String password = "mypass";

        FileSystemManager fsManager = null;
        FileObject remoteDirObject = null;
        FileSystemOptions opts = new FileSystemOptions();

        try {
            fsManager = VFS.getManager();

            StaticUserAuthenticator auth = new StaticUserAuthenticator(null, username, password);
            DefaultFileSystemConfigBuilder.getInstance().setUserAuthenticator(opts, auth);
            FtpFileSystemConfigBuilder.getInstance().setPassiveMode(opts, true);

            log.info("Próba połączenia z serwerem FTP: " + serverAddress);

            remoteDirObject = fsManager.resolveFile(serverAddress + directory, opts);

            log.info("Typ pliku: " + remoteDirObject.getType());

            if (!remoteDirObject.exists()) {
                remoteDirObject.createFolder();
                log.info("Katalog utworzony pomyślnie: " + directory);
            } else {
                log.info("Katalog już istnieje: " + directory);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (remoteDirObject != null) {
                try {
                    remoteDirObject.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}