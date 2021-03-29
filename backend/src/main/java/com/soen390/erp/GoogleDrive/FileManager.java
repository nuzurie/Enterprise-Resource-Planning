package com.soen390.erp.GoogleDrive;

import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class FileManager {

    GoogleDriveManager googleDriveManager;

    public String uploadFile(MultipartFile file, String filePath)
    {
        try
        {
//            String folderId = getFolderId(filePath);
            if (null != file) {
                File fileMetadata = new File();
//                fileMetadata.setParents(Collections.singletonList(folderId));
                fileMetadata.setName(file.getOriginalFilename());
                File uploadFile = googleDriveManager.getInstance()
                        .files()
                        .create(fileMetadata, new InputStreamContent(
                                file.getContentType(),
                                new ByteArrayInputStream(file.getBytes()))
                        )
                        .setFields("id").execute();
                return uploadFile.getId();
            }
        } catch (Exception e)
        {
            System.out.println("Error: " + e);
        }
        return null;
    }

    public void downloadFile(String id, OutputStream outputStream)
            throws IOException, GeneralSecurityException
    {
        if (id != null) {
            String fileId = id;
            googleDriveManager.getInstance().files().get(fileId)
                    .executeMediaAndDownloadTo(outputStream);
        }
    }
}
