package in.co.rays.project_3.model;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import in.co.rays.project_3.dto.ShoppingCartDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class ShoppingCartModelHibImp implements ShoppingCartModelInt {

    public long add(ShoppingCartDTO dto) throws ApplicationException, DuplicateRecordException {
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
            throw new ApplicationException("Exception in ShoppingCart Add " + e.getMessage());
        } finally {
            HibDataSource.closeSession(session);
        }

        return pk;
    }

    public void delete(ShoppingCartDTO dto) throws ApplicationException {
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
            throw new ApplicationException("Exception in ShoppingCart Delete" + e.getMessage());
        } finally {
            HibDataSource.closeSession(session);
        }
    }

    public void update(ShoppingCartDTO dto) throws ApplicationException {
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
            throw new ApplicationException("Exception in ShoppingCart Update" + e.getMessage());
        } finally {
            HibDataSource.closeSession(session);
        }
    }

    public ShoppingCartDTO findByProduct(String product) throws ApplicationException {
        Session session = null;
        ShoppingCartDTO dto = null;
        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(ShoppingCartDTO.class);
            criteria.add(Restrictions.eq("product", product));
            List list = criteria.list();
            if (!list.isEmpty()) {
                dto = (ShoppingCartDTO) list.get(0);
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new ApplicationException("Exception in ShoppingCart FindByProduct " + e.getMessage());
        } finally {
            HibDataSource.closeSession(session);
        }
        return dto;
    }

    public ShoppingCartDTO findByPK(long pk) throws ApplicationException {
        Session session = null;
        ShoppingCartDTO dto = null;
        try {
            session = HibDataSource.getSession();
            dto = (ShoppingCartDTO) session.get(ShoppingCartDTO.class, pk);
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new ApplicationException("Exception in ShoppingCart FindByPK " + e.getMessage());
        } finally {
            HibDataSource.closeSession(session);
        }
        return dto;
    }

    public List search(ShoppingCartDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    public List search(ShoppingCartDTO dto, int pageNo, int pageSize) throws ApplicationException {
        Session session = null;
        List list = null;
        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(ShoppingCartDTO.class);
            if (dto != null) {
                if (dto.getId() > 0) {
                    criteria.add(Restrictions.eq("id", dto.getId()));
                }
                if (dto.getName() != null && !dto.getName().isEmpty()) {
                    criteria.add(Restrictions.like("name", dto.getName() + "%"));
                }
                if (dto.getProduct() != null && !dto.getProduct().isEmpty()) {
                    criteria.add(Restrictions.like("product", dto.getProduct() + "%"));
                }
                if (dto.getCategory() != null && !dto.getCategory().isEmpty()) {
                    criteria.add(Restrictions.like("category", dto.getCategory() + "%"));
                }
            }
            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }
            list = criteria.list();
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new ApplicationException("Exception in ShoppingCart Search " + e.getMessage());
        } finally {
            HibDataSource.closeSession(session);
        }
        return list;
    }

    public List list() throws ApplicationException {
        return list(0, 0);
    }

    public List list(int pageNo, int pageSize) throws ApplicationException {
        Session session = null;
        List list = null;
        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(ShoppingCartDTO.class);
            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }
            list = criteria.list();
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new ApplicationException("Exception in ShoppingCart List " + e.getMessage());
        } finally {
            HibDataSource.closeSession(session);
        }
        return list;
    }
}
