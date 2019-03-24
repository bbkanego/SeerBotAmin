package com.seerlogics.botadmin.repository;

import com.seerlogics.botadmin.model.Account;
import com.seerlogics.botadmin.model.Category;
import com.seerlogics.botadmin.model.Intent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by bkane on 3/14/19.
 */
@Repository
public interface IntentRepository extends JpaRepository<Intent, Long>, IntentSearchRepository {
    List<Intent> findByCategory(Category cat);

    @Query("select pu from Intent pu where pu.category.code = :code")
    List<Intent> findIntentsByCode(@Param("code") String code);

    @Query("select pu from Intent pu where pu.category.code = :code and pu.intentType = :intentType")
    List<Intent> findIntentsByCodeAndType(@Param("code") String code, @Param("intentType") String intentType);

    @Query("select pu from Intent pu where pu.category.code = :code and pu.intentType = :intentType " +
            "and pu.owner = :owner")
    List<Intent> findIntentsByCodeTypeAndOwner(@Param("code") String code, @Param("intentType") String intentType,
                                               @Param("owner") Account owner);
}
