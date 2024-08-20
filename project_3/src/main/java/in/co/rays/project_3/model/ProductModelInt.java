package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.ProductDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.exception.RecordNotFoundException;

/**
 * Interface of Product model
 * 
 * @author Your Name
 *
 */
public interface ProductModelInt {

    public long add(ProductDTO dto) throws ApplicationException, DuplicateRecordException;

    public void delete(ProductDTO dto) throws ApplicationException;

    public void update(ProductDTO dto) throws ApplicationException, DuplicateRecordException;

    public ProductDTO findByPK(long pk) throws ApplicationException;

    public List<ProductDTO> list() throws ApplicationException;

    public List<ProductDTO> list(int pageNo, int pageSize) throws ApplicationException;

    public List<ProductDTO> search(ProductDTO dto, int pageNo, int pageSize) throws ApplicationException;

}
