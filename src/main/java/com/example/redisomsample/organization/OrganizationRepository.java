package com.example.redisomsample.organization;

import com.redis.om.spring.annotations.Query;
import com.redis.om.spring.repository.RedisDocumentRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface OrganizationRepository extends RedisDocumentRepository<Organization, String> {

    List<Organization> findAllBySupervisorId(String id);

    List<Organization> search(@Param("text") String descText);

    @Query("$text @tags:{$tags}")
    List<Organization> search(@Param("text") String descText, @Param("tags") Collection<String> tags);

    @Query("@tags:{$tags}")
    List<Organization> searchByTags(@Param("tags") Collection<String> tags);

    @Override
    List<Organization> findAll();

    List<String> getAllTags();
}
