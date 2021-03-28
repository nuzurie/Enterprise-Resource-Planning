package com.soen390.erp.GoogleDrive;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class GoogleDriveController {

    FileManager fileManager;

    @PostMapping(value = "/upload",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE} )
    public ResponseEntity<String> uploadSingleFileExample4(@RequestBody MultipartFile file, @RequestParam(required = false) String path)
    {
        System.out.println("Request contains, File: " + file.getOriginalFilename());
        String fileId = fileManager.uploadFile(file, path);
        if(fileId == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok("Success, FileId: "+ fileId);
    }

    @GetMapping("/download/{id}")
    public void download(@PathVariable String id, HttpServletResponse response) throws IOException, GeneralSecurityException
    {
        fileManager.downloadFile(id, response.getOutputStream());
    }
}
