package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.FileUploadDao;
import ar.edu.itba.paw.interfaces.FileUploadService;
import ar.edu.itba.paw.models.UploadFile;
import ar.edu.itba.paw.persistence.FileUploadDaoImpl;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by estebankramer on 03/10/2018.
 */

@Service
public class FileUploadServiceImpl implements FileUploadService{

    @Autowired
    private FileUploadDao fileUploadDao;

    public FileUploadServiceImpl() {

    }

    @Transactional
    public void save(UploadFile uploadFile) {
        fileUploadDao.save(uploadFile);
    }

}
