package com.example.redisomsample.organization;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface OrganizationService {

    Optional<Organization> getOrganization(String id);

    List<Organization> findAllBySupervisorId(String id);

    List<Organization> findAll();

    List<Organization> searchByText(String text);

    List<Organization> searchByTags(Collection<String> tags);

    List<Organization> searchByText(String text, Collection<String> tags);

    Organization save(Organization bean);

    List<String> getOrganizationTags();

    List<Organization> findAllByStatus(Organization.OrganizationStatus status);

    void resetOrganizationIndex();
}
