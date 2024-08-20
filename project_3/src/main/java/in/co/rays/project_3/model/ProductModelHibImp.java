package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.ProductDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.exception.RecordNotFoundException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate implementation of Product model
 * 
 */
public class ProductModelHibImp implements ProductModelInt{

    public long add(ProductDTO dto) throws ApplicationException, DuplicateRecordException {
        Session session = null;
        Transaction tx = null;
        long pk = 0;
        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            pk = (long) session.save(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new ApplicationException("Exception in Product Add: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return pk;
    }

    public void update(ProductDTO dto) throws ApplicationException, DuplicateRecordException {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.update(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new ApplicationException("Exception in Product Update: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void delete(ProductDTO dto) throws ApplicationException {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.delete(dto);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new ApplicationException("Exception in Product Delete: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public ProductDTO findByPK(long pk) throws ApplicationException {
        Session session = null;
        ProductDTO dto = null;
        try {
            session = HibDataSource.getSession();
            dto = (ProductDTO) session.get(ProductDTO.class, pk);
        } catch (HibernateException e) {
            throw new ApplicationException("Exception in getting Product by PK: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return dto;
    }

    public List<ProductDTO> list() throws ApplicationException {
        return list(0, 0);
    }

    public List<ProductDTO> list(int pageNo, int pageSize) throws ApplicationException {
        Session session = null;
        List<ProductDTO> list = null;
        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(ProductDTO.class);
            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }
            list = criteria.list();
        } catch (HibernateException e) {
            throw new ApplicationException("Exception in Product list: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }
    
    
    public List<ProductDTO> search(ProductDTO dto, int pageNo, int pageSize) throws ApplicationException {
        Session session = null;
        List<ProductDTO> list = new ArrayList<ProductDTO>();
        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(ProductDTO.class);

            if (dto != null) {
                if (dto.getId() > 0) {
                    criteria.add(Restrictions.eq("id", dto.getId()));
                }
                if (dto.getName() != null && dto.getName().trim().length() > 0) {
                    criteria.add(Restrictions.ilike("name", dto.getName(), MatchMode.ANYWHERE));
                }
                if (dto.getDescription() != null && dto.getDescription().trim().length() > 0) {
                    criteria.add(Restrictions.ilike("description", dto.getDescription(), MatchMode.ANYWHERE));
                }
                if (dto.getPrice() > 0) {
                    criteria.add(Restrictions.eq("price", dto.getPrice()));
                }
                if (dto.getDateOfPurchase() != null) {
                    criteria.add(Restrictions.eq("dateOfPurchase", dto.getDateOfPurchase()));
                }
                if (dto.getCategory() != null && dto.getCategory().trim().length() > 0) {
                    criteria.add(Restrictions.ilike("category", dto.getCategory(), MatchMode.ANYWHERE));
                }
            }

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();
        } catch (HibernateException e) {
            throw new ApplicationException("Exception in Product search: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }


    // Additional methods for specific queries or operations related to products can be added here.

}
