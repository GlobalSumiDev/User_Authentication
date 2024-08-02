package com.GlobalSumi.Internal.UserFile.File.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.GlobalSumi.Internal.UserFile.File.model.File;
import com.GlobalSumi.Internal.UserFile.File.model.Folder;
import com.GlobalSumi.Internal.UserFile.File.service.FileService;
import com.GlobalSumi.Internal.UserFile.File.service.FolderService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = "http://localhost:4200/")
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private FolderService folderService;

    @GetMapping
    public List<File> getFilesByFolder(@RequestParam String folderName, @RequestParam String userEmail) {
        return fileService.getFilesByFolderNameAndUserEmail(folderName, userEmail);
    }

    @PostMapping("/upload")
    public File uploadFile(@RequestParam("file") MultipartFile file,
                           @RequestParam("folderName") String folderName,
                           @RequestParam("userEmail") String userEmail) throws IOException {
        Folder folder = folderService.getFolderByNameAndUserEmail(folderName, userEmail);

        if (folder == null) {
            throw new RuntimeException("Folder not found");
        }

        File fileEntity = new File();
        fileEntity.setName(file.getOriginalFilename());
        fileEntity.setUserEmail(userEmail);
        fileEntity.setSize(file.getSize());
        fileEntity.setFolder(folderName); // Set folder entity
        fileEntity.setData(file.getBytes()); // Save file data as blob
        fileEntity.setUploadTime(LocalDateTime.now()); // Set upload time

        return fileService.uploadFile(fileEntity);
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadFile(@RequestParam String fileName,@RequestParam String folderName, @RequestParam String userEmail) {
        File file = fileService.getfilebyNameAndUserEmail(fileName,folderName,userEmail);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
                .body(file.getData());
    }

    @DeleteMapping
    public void deleteFile(@RequestParam String fileName,@RequestParam String folderName, @RequestParam String userEmail) {
        fileService.deleteFile( fileName, folderName, userEmail);
    }
}