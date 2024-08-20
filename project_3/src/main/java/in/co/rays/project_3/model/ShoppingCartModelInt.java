package in.co.rays.project_3.model;

import java.util.List;
import in.co.rays.project_3.dto.ShoppingCartDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.exception.RecordNotFoundException;

public interface ShoppingCartModelInt {

    public long add(ShoppingCartDTO dto) throws ApplicationException, DuplicateRecordException;

    public void delete(ShoppingCartDTO dto) throws ApplicationException;

    public void update(ShoppingCartDTO dto) throws ApplicationException;

    public ShoppingCartDTO findByPK(long pk) throws ApplicationException;

    public ShoppingCartDTO findByProduct(String product) throws ApplicationException;

    public List list() throws ApplicationException;

    public List list(int pageNo, int pageSize) throws ApplicationException;

    public List search(ShoppingCartDTO dto) throws ApplicationException;

    public List search(ShoppingCartDTO dto, int pageNo, int pageSize) throws ApplicationException;
}
