package org.farid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.farid.model.entity.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAll();

    Optional<Product> findById(Long id);

    @Query(value =
            "select  r.id ,r.is_show, r.comment_is_enable, r.vote_is_enable,r.comment_is_public,r.vote_is_public, r.title ,r.rate ,r.count , c.comment, r.image from (\n" +
                    "\tSELECT \t\n" +
                    "\t ROW_NUMBER() OVER (PARTITION BY product_id ORDER BY id desc)  row_num\n" +
                    "\t,comment, product_id FROM comment\n" +
                    "\t )\n" +
                    "\tc\n" +
                    "\tright join  (\n" +
                    "select \n" +
                    " p.is_show, p.comment_is_enable, p.vote_is_enable,p.comment_is_public,p.vote_is_public, p.title , p.id , p.image, \n" +
                    "\t\t\t\t\t(select casewhen( avg(v.rate) is NULL , 0 ,avg(v.rate) ) from vote v where v.product_id = p.id) rate ,\n" +
                    "\t\t\t\t\t(select count(*) from comment c where c.product_id = p.id) count\n" +
                    "\t\t\t\t\tfrom product p"+
                    " where p.category_id =:categoryId "+
                    "\t\t\t\t\tlimit :limit offset :offset\t\t\t\t\t\n" +
                    "\t\t\t) r\n" +
                    "on c.product_id = r.id\n" +
                    "where c.row_num < :commentLimit or c.row_num is NULL", nativeQuery = true)
    List<Object[]> review(@Param("limit") Integer limit,
                          @Param("offset") Integer offset,
                          @Param("commentLimit") Integer commentLimit,
                          @Param("categoryId") Integer categoryId);

}
