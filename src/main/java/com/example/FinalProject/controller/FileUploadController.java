package com.example.FinalProject.controller;

import com.example.FinalProject.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestBody(required = false) MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please select a file to upload.");
        }

        if (!isValidImage(file)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid file type. Only JPG, PNG, and GIF are allowed.");
        }

        if (file.getSize() > 5 * 1024 * 1024) { // 5MB limit
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File size exceeds limit of 5MB.");
        }

        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/files/download/")
                .path(fileName)
                .toUriString();

        return ResponseEntity.status(HttpStatus.OK).body("File uploaded successfully: " + fileDownloadUri);
    }

    private boolean isValidImage(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType.equals("image/jpeg") || contentType.equals("image/png") || contentType.equals("image/gif");
    }
}
