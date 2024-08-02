package com.GlobalSumi.Internal.UserFile.File.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.GlobalSumi.Internal.UserFile.File.model.File;
import com.GlobalSumi.Internal.UserFile.File.model.Folder;

public interface FileRepository extends JpaRepository<File, Long>  {
	
	List<File> findByFolderAndUserEmail(String folderName, String userEmail);
	
	File findByNameAndFolderAndUserEmail(String fileName, String folderName, String userEmail);

	void deleteByNameAndFolderAndUserEmail(String fileName, String folderName, String userEmail);
}
