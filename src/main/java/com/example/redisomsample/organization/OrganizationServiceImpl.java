package com.example.redisomsample.organization;

import com.redis.om.spring.RediSearchIndexer;
import com.redis.om.spring.client.RedisModulesClient;
import com.redis.om.spring.ops.search.SearchOperations;
import com.redis.om.spring.ops.search.SearchOperationsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private RedisModulesClient redisModulesClient;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ApplicationContext context;

    @Override
    public List<Organization> findAllBySupervisorId(String id) {
        return organizationRepository.findAllBySupervisorId(id);
    }

    @Override
    public Optional<Organization> getOrganization(String id){
        return organizationRepository.findById(id);
    }

    @Override
    public List<Organization> findAll() {
        return organizationRepository.findAll();
    }

    @Override
    public List<Organization> searchByText(String text) {
        return organizationRepository.search(text);
    }

    @Override
    public List<Organization> searchByTags(Collection<String> tags) {
        return organizationRepository.searchByTags(tags);
    }

    @Override
    public List<Organization> searchByText(String text, Collection<String> tags) {
        return organizationRepository.search(text, tags);
    }

    @Override
    public Organization save(Organization bean) {
       return organizationRepository.save(bean);
    }

    @Override
    public List<String> getOrganizationTags() {
        return organizationRepository.getAllTags();
    }

    @Override
    public List<Organization> findAllByStatus(Organization.OrganizationStatus status) {
        return organizationRepository.findAll()
                .stream()
                .filter(org -> org.getStatus().equals(status))
                .collect(Collectors.toList());
    }

    @Override
    public void resetOrganizationIndex() {
        SearchOperations<String> operations = new SearchOperationsImpl<>("serve.organization.OrganizationIdx", redisModulesClient, redisTemplate);
        operations.dropIndex();
        RediSearchIndexer indexer = new RediSearchIndexer(context);
        indexer.createIndexFor(Organization.class);
    }
}
