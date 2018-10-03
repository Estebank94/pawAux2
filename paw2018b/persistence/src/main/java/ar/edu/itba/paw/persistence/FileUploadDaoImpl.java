package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.FileUploadDao;
import ar.edu.itba.paw.models.UploadFile;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by estebankramer on 03/10/2018.
 */

@Repository
public class FileUploadDaoImpl implements FileUploadDao {
    @Autowired
    private SessionFactory sessionFactory;

    public FileUploadDaoImpl() {
    }

    public FileUploadDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void save(UploadFile uploadFile) {
        sessionFactory.getCurrentSession().save(uploadFile);
    }
}
