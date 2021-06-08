package com.project.cms.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class FilesStorageService implements IFilesStorageService {

    private final Path cvUpload = Paths.get("cv");
    private final Path  timetableUpload = Paths.get("timetable");

    @Override
    public void init() {
        try {
            Files.createDirectory(cvUpload);
            Files.createDirectory(timetableUpload);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public void saveCv(MultipartFile file, String name) {
        try {
            Files.copy(file.getInputStream(), this.cvUpload.resolve(name), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage()+" "+file.getOriginalFilename());
        }
    }

    @Override
    public Resource loadCv(String filename) {
        try {
            Path file = cvUpload.resolve(filename);
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
    public void saveTimetable(MultipartFile file, String name) {
        try {
            Files.copy(file.getInputStream(), this.timetableUpload.resolve(name));
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    @Override
    public Resource loadTimetable(String filename) {
        try {
            Path file = timetableUpload.resolve(filename);
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
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.cvUpload, 1).filter(path -> !path.equals(this.cvUpload)).map(this.cvUpload::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }
}