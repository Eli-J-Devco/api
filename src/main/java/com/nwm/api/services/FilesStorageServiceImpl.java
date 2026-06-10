/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

//import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;
import java.io.FileOutputStream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {

	private final Path root = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
			Constants.uploadRootPathConfigKey));
//	private final Path root =  Constants.uploadRootPathConfigKey;
	

	@Override
	public void init() {
		
		try {
			Files.createDirectory(root);
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize folder for upload!");
		}
	}

	@Override
	public void save(MultipartFile file) {
		try {
            
			Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
			
			String gzipFile =  this.root.resolve(file.getOriginalFilename()).toString();
			FileInputStream fis = new FileInputStream(gzipFile);
			GZIPInputStream gis = new GZIPInputStream(fis);
			 
			 
			 FileOutputStream fos = new FileOutputStream(this.root.resolve("abc.log").toString());
			 
			 byte[] buffer = new byte[1024];
	            int len;
	            while((len = gis.read(buffer)) != -1){
	                fos.write(buffer, 0, len);
	            }
	            //close resources
	            fos.close();
	            gis.close();
	            
			
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}
	}

	@Override
	public Resource load(String filename) {
		try {
			Path file = root.resolve(filename);
			Resource resource = new UrlResource(file.toUri());

			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("Could not read the file!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("Error: " + e.getMessage());
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(root.toFile());
	}

	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
		} catch (IOException e) {
			throw new RuntimeException("Could not load the files!");
		}
	}

}