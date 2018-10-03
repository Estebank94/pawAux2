package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.UploadFile;

/**
 * Created by estebankramer on 03/10/2018.
 */
public interface FileUploadDao {
    void save(UploadFile uploadFile);
}
