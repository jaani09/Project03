package in.co.rays.project_3.model;

import in.co.rays.project_3.dto.JobDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

import java.util.List;

/**
 * JobModelInt provides interface for Job model.
 *
 */
public interface JobModelInt {

    /**
     * Adds a Job
     * 
     * @param dto
     * @return
     * @throws ApplicationException
     * @throws DuplicateRecordException
     */
    long add(JobDTO dto) throws ApplicationException, DuplicateRecordException;

    /**
     * Updates a Job
     * 
     * @param dto
     * @throws ApplicationException
     * @throws DuplicateRecordException
     */
    void update(JobDTO dto) throws ApplicationException, DuplicateRecordException;

    /**
     * Deletes a Job
     * 
     * @param dto
     * @throws ApplicationException
     */
    void delete(JobDTO dto) throws ApplicationException;

    /**
     * Finds Job by primary key
     * 
     * @param pk
     * @return
     * @throws ApplicationException
     */
    JobDTO findByPK(long pk) throws ApplicationException;

    /**
     * Searches Jobs with pagination.
     * 
     * @param dto
     * @param pageNo
     * @param pageSize
     * @return
     * @throws ApplicationException
     */
    List<JobDTO> search(JobDTO dto, int pageNo, int pageSize) throws ApplicationException;

    /**
     * Searches Jobs
     * 
     * @param dto
     * @return
     * @throws ApplicationException
     */
    List<JobDTO> search(JobDTO dto) throws ApplicationException;

    /**
     * Gets List of Jobs
     * 
     * @return
     * @throws ApplicationException
     */
    List<JobDTO> list() throws ApplicationException;

    /**
     * Gets List of Jobs with pagination
     * 
     * @param pageNo
     * @param pageSize
     * @return
     * @throws ApplicationException
     */
    List<JobDTO> list(int pageNo, int pageSize) throws ApplicationException;

}
