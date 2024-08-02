package com.GlobalSumi.Internal.UserFile.File.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GlobalSumi.Internal.UserFile.File.model.Folder;
import com.GlobalSumi.Internal.UserFile.File.repository.FolderRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class FolderService {

    private static final Logger logger = LoggerFactory.getLogger(FolderService.class);

    @Autowired
    private FolderRepository folderRepository;

    public List<Folder> getFoldersByUserEmail(String userEmail) {
        logger.info("Retrieving folders for user email: {}", userEmail);
        List<Folder> folders = folderRepository.findByUserEmail(userEmail);
        logger.info("Retrieved folders: {}", folders);
        return folders;
    }

    public Folder createFolder(Folder folder) {
        logger.info("Creating folder: {}", folder);
        Folder savedFolder = folderRepository.save(folder);
        logger.info("Saved folder: {}", savedFolder);
        return savedFolder;
    }

    public Folder getFolderByNameAndUserEmail(String folderName, String userEmail) {
        logger.info("Checking if folder exists with name: {} and email: {}", folderName, userEmail);
        Folder folder = folderRepository.findByFolderNameAndUserEmail(folderName, userEmail);
        logger.info("Found folder: {}", folder);
        return folder;
    }
    
    public String deleteFolder(String folderName, String userEmail) {
        logger.info("Deleting folder: {}",  folderName,  userEmail);
        folderRepository.deleteByFolderNameAndUserEmail(folderName, userEmail);
        return "Folder Deleted sucesfully" ;
    }
}
