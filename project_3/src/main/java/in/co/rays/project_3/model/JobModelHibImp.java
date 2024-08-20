package in.co.rays.project_3.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.JobDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate implementation of JobModel
 * 
 */
public class JobModelHibImp implements JobModelInt {

	/**
	 * Add a Job
	 * 
	 * @param dto
	 * @return pk of Job
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public long add(JobDTO dto) throws ApplicationException, DuplicateRecordException {
		Session session = null;
		Transaction transaction = null;
		long pk = 0;

		try {
			session = HibDataSource.getSession();
			transaction = session.beginTransaction();
			session.save(dto);
			pk = dto.getId();
			transaction.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
			throw new ApplicationException("Exception in Job Add " + e.getMessage());
		} finally {
			HibDataSource.closeSession(session);
		}

		return pk;
	}

	/**
	 * Delete a Job
	 * 
	 * @param dto
	 * @throws ApplicationException
	 */
	public void delete(JobDTO dto) throws ApplicationException {
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibDataSource.getSession();
			transaction = session.beginTransaction();
			session.delete(dto);
			transaction.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
			throw new ApplicationException("Exception in Job Delete" + e.getMessage());
		} finally {
			HibDataSource.closeSession(session);
		}
	}

	/**
	 * Update a Job
	 * 
	 * @param dto
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public void update(JobDTO dto) throws ApplicationException {
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibDataSource.getSession();
			transaction = session.beginTransaction();
			session.update(dto);
			transaction.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
			throw new ApplicationException("Exception in Job Update" + e.getMessage());
		} finally {
			HibDataSource.closeSession(session);
		}
	}

	/**
	 * Find Job by Title
	 * 
	 * @param title
	 *            : get parameter
	 * @return dto
	 * @throws ApplicationException
	 */
	public JobDTO findByTitle(String title) throws ApplicationException {
		Session session = null;
		JobDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(JobDTO.class);
			criteria.add(Restrictions.eq("title", title));
			List list = criteria.list();
			Iterator it = list.iterator();
			while (it.hasNext()) {
				dto = (JobDTO) it.next();
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in Job FindByTitle " + e.getMessage());
		} finally {
			HibDataSource.closeSession(session);
		}
		return dto;
	}

	/**
	 * Find Job by PK
	 * 
	 * @param pk
	 *            : get parameter
	 * @return dto
	 * @throws ApplicationException
	 */
	public JobDTO findByPK(long pk) throws ApplicationException {
		Session session = null;
		JobDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (JobDTO) session.get(JobDTO.class, pk);
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in Job FindByPK " + e.getMessage());
		} finally {
			HibDataSource.closeSession(session);
		}
		return dto;
	}

	/**
	 * Search Job
	 * 
	 * @param dto
	 *            : Search Parameters
	 * @return list : List of Jobs
	 * @throws ApplicationException
	 */
	public List search(JobDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	/**
	 * Search Job with pagination
	 * 
	 * @return list : List of Jobs
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * @throws ApplicationException
	 */
	public List search(JobDTO dto, int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(JobDTO.class);
			if (dto != null) {
				if (dto.getId() != null) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}
				if (dto.getTitle() != null && dto.getTitle().length() > 0) {
					criteria.add(Restrictions.like("title", dto.getTitle() + "%"));
				}
				if (dto.getOpening() != null) {
					criteria.add(Restrictions.eq("opening", dto.getOpening()));
				}
				if (dto.getExperience() > 0) {
					criteria.add(Restrictions.eq("experience", dto.getExperience()));
				}
				if (dto.getStatus() != null && dto.getStatus().length() > 0) {
					criteria.add(Restrictions.like("status", dto.getStatus() + "%"));
				}
			}
			// Pagination
			if (pageSize > 0) {
				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in Job Search " + e.getMessage());
		} finally {
			HibDataSource.closeSession(session);
		}
		return list;
	}

	/**
	 * Get List of Job
	 * 
	 * @return list : List of Jobs
	 * @throws ApplicationException
	 */
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 * Get List of Job with pagination
	 * 
	 * @return list : List of Jobs
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * @throws ApplicationException
	 */
	public List list(int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(JobDTO.class);

			// Pagination
			if (pageSize > 0) {
				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in Job List " + e.getMessage());
		} finally {
			HibDataSource.closeSession(session);
		}
		return list;
	}

	/**
	 * Get List of Job with pagination
	 * 
	 * @return list : List of Jobs
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * @throws ApplicationException
	 */
	public List list1(int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(JobDTO.class);

			// Pagination
			if (pageSize > 0) {
				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in Job List " + e.getMessage());
		} finally {
			HibDataSource.closeSession(session);
		}
		return list;
	}

	public List getActiveJobs() throws ApplicationException {
		Session session = null;
		List list = new ArrayList();
		Date d = new Date();
		try {
			session = HibDataSource.getSession();
			Query q = session.createQuery("from JobDTO where opening > '" + d + "'");
			list = q.list();
		} catch (Exception e) {
			throw new ApplicationException("Exception in  Job getActiveJobs " + e.getMessage());
		} finally {
			HibDataSource.closeSession(session);
		}
		return list;
	}

}

