package com.GlobalSumi.Internal.UserFile.File.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity(name = "UserFile")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String userEmail; // Email of the user who uploaded the file
    
    @Column(name = "Upload_Time")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime uploadTime;
    
    @Column(name = "File_Size")
    private long size;
    
    @Lob
    @Column(name = "DATA")
    private byte[] data;
    
    @Column(name = "FOLDER_NAME")
    private String folder;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folderName) {
		this.folder = folderName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public LocalDateTime getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(LocalDateTime uploadTime) {
		this.uploadTime = uploadTime;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	
    // Getters and setters
    
}
