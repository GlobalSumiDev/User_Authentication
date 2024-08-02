package com.GlobalSumi.Internal.UserFile.File.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.GlobalSumi.Internal.UserFile.File.model.File;
import com.GlobalSumi.Internal.UserFile.File.model.Folder;
import com.GlobalSumi.Internal.UserFile.File.repository.FileRepository;
import com.GlobalSumi.Internal.UserFile.File.repository.FolderRepository;

import jakarta.transaction.Transactional;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FolderRepository folderRepository;

    public List<File> getFilesByFolderNameAndUserEmail(String folderName, String userEmail){
        Folder folder = folderRepository.findByFolderNameAndUserEmail(folderName, userEmail);
        if (folder != null) {
            return fileRepository.findByFolderAndUserEmail(folderName,userEmail);
        }
        return List.of(); // return empty list if folder not found
    }

    public File uploadFile(File file) {
        return fileRepository.save(file);
    }
    
    @Transactional
    public File getfilebyNameAndUserEmail(String fileName, String folderName, String userEmail) {
        return fileRepository.findByNameAndFolderAndUserEmail(fileName,folderName,userEmail);
    }

    @Modifying
    @Transactional
    public String deleteFile( String fileName, String folderName,  String userEmail) {
        
    	File files = fileRepository.findByNameAndFolderAndUserEmail(fileName,folderName,userEmail);
    	if(files != null) {
    	fileRepository.deleteByNameAndFolderAndUserEmail( fileName, folderName, userEmail);
    	return "File Deleted SUcesfully";
    	}
    	return "File Not Found";
    }
}
