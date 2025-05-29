package vn.edu.hcmuaf.st.DACN_BookStore_2025.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmuaf.st.DACN_BookStore_2025.entity.CategoryEntity;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    public CategoryEntity findByCategoryID(int id);

    @Transactional
    @Modifying
    public void deleteByCategoryID(int id);

    @Transactional
    @Modifying
    @Query(value = "update category set name=:name, code=:code, created_at=:createdAt, updated_at=:updatedAt where categoryID=:id", nativeQuery = true)
    public void updateCategory(@Param("name") String name,
                               @Param("code") String code,
                               @Param("createdAt") LocalDate createdAt,
                               @Param("updatedAt") LocalDate updatedAt,
                               @Param("id") int id);

    public List<CategoryEntity> findFirst10ByOrderByCategoryIDAsc();
}
