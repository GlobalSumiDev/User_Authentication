package com.GlobalSumi.Internal.UserFile.File.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.GlobalSumi.Internal.UserFile.File.model.Folder;




public interface FolderRepository extends JpaRepository<Folder, Long> {
	List<Folder> findByUserEmail(String userEmail);

	Folder findByFolderNameAndUserEmail(String folderName, String userEmail);

	void deleteByFolderNameAndUserEmail(String folderName, String userEmail);
	
}