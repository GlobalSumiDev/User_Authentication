package com.GlobalSumi.Internal.UserFile.File.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.GlobalSumi.Internal.UserFile.File.model.Folder;
import com.GlobalSumi.Internal.UserFile.File.service.FolderService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/folders")
@CrossOrigin(origins = "http://localhost:4200/")
public class FolderController {

    private static final Logger logger = LoggerFactory.getLogger(FolderController.class);

    @Autowired
    private FolderService folderService;

    @GetMapping
    public List<Folder> getFoldersByUserEmail(@RequestParam String userEmail) {
        logger.info("Getting folders for user email: {}", userEmail);
        List<Folder> folders = folderService.getFoldersByUserEmail(userEmail);
        logger.info("Retrieved folders: {}", folders);
        return folders;
    }

    @PostMapping
    public Folder createFolder(@RequestBody Folder folder) {
        logger.info("Request to create folder: {}", folder);

        // Check if folder already exists
        Folder existingFolder = folderService.getFolderByNameAndUserEmail(folder.getFolderName(), folder.getUserEmail());
        if (existingFolder != null) {
            logger.warn("Folder already exists: {}", existingFolder);
            return null;  // Optionally, handle this case differently
        }

        Folder createdFolder = folderService.createFolder(folder);
        logger.info("Created folder: {}", createdFolder);
        return createdFolder;
    }
    
    @DeleteMapping
    public String deleteFolder(@RequestBody Folder folder) {
    	 logger.info("Request to delete folder: {}", folder);
    	 Folder existingFolder = folderService.getFolderByNameAndUserEmail(folder.getFolderName(), folder.getUserEmail());
         if (existingFolder != null) {
             logger.warn("Folder exists: {}", existingFolder);
             return "Folder Delted Sucesfully";  // Optionally, handle this case differently
         }
		return "Folder Not Found";
    }
    
}
