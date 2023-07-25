package com.example.redisomsample.organization;

import com.example.redisomsample.RedisOmSampleApplication;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Point;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SpringBootTest(classes = {RedisOmSampleApplication.class})
class OrganizationRepositoryTest {

    @Autowired
    private OrganizationRepository repo;

    final Organization organization1 = new Organization("1", "Daily Bread",Organization.OrganizationType.NON_PROFIT,
            Organization.OrganizationStatus.ACTIVE,"testSupervisorId","url", "address",
            "city", "VA", "zipcode", new Point(1,2), "Feeding specialkeyword people",
            Set.of("tag1"), "photo", 5.0, new HashMap<>(), LocalDate.now(),"username" );
    final Organization organization2 = new Organization("2", "Thomas Road",Organization.OrganizationType.CHURCH,
            Organization.OrganizationStatus.ACTIVE,"testSupervisorId","url", "address",
            "city", "VA", "zipcode", new Point(1,2), "A specialkeyword church",
            new HashSet<>(), "photo", 5.0, new HashMap<>(), LocalDate.now(),"username" );
    final Organization organization3 = new Organization("3", "Habitat for Humanity",Organization.OrganizationType.NON_PROFIT,
            Organization.OrganizationStatus.ACTIVE,"testSupervisorId","url", "address",
            "city", "VA", "zipcode", new Point(1,2), "Building houses across the country",
            Set.of("tag2"), "photo", 5.0, new HashMap<>(), LocalDate.now(),"username" );
    final Organization organization4 = new Organization("4", "SpecialKeyword",Organization.OrganizationType.CIVIC,
            Organization.OrganizationStatus.ACTIVE,"testSupervisorId","url", "address",
            "city", "VA", "zipcode", new Point(1,2), "Doing something",
            Set.of("tag1", "tag2"), "photo", 5.0, new HashMap<>(), LocalDate.now(),"username" );

    final List<Organization> orgList = List.of(organization1, organization2, organization3, organization4);

    @BeforeEach
    public void createTestData() {
        repo.saveAll(orgList);
    }

    @AfterEach
    public void removeTestData() {
        repo.deleteAll(orgList);
    }

    @Test
    void searchByText_should_perform_a_text_search_on_descriptions_and_names() {
        List<Organization> resultList = repo.search("specialkeyword");
        assertEquals(3, resultList.size(),
                "Result should contain 3 results that match specialkeyword");

        assertTrue(resultList.stream()
                        .map(org -> org.getId())
                        .collect(Collectors.toList()).containsAll(List.of("1", "2", "4")),
                "Should contain orgs with the keyword in name or description");
    }

    @Test
    void searchByTextAndTags_should_perform_a_text_and_tag_search() {
        List<Organization> resultList = repo.search("specialkeyword", Set.of("tag2"));
        assertEquals(1, resultList.size(),
                "Result should contain 1 result that match specialkeyword and tag2");

        assertTrue(resultList.stream()
                        .map(org -> org.getId())
                        .collect(Collectors.toList()).containsAll(List.of("4")),
                "Should contain orgs with the keyword in name or description and the tag");
    }

    @Test
    void should_search_by_tags() {
        List<Organization> results = repo.searchByTags(Set.of("tag1"));
        assertEquals(2, results.size(), "Should have 2 results with this tag");

        assertTrue(results.stream()
                        .map(org -> org.getId())
                        .collect(Collectors.toList()).containsAll(List.of("1", "4")),
                "Should contain orgs with the selected tag");
    }
}