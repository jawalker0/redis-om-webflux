package com.example.redisomsample.organization;

import com.redis.om.spring.annotations.Document;
import com.redis.om.spring.annotations.Indexed;
import com.redis.om.spring.annotations.Searchable;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@RequiredArgsConstructor(staticName = "of")
@Data
@Document("organization")
public class Organization {

    public static final String indexName = "serve.organization.OrganizationIdx";

    @Id
    @Indexed
    private String id;

    @NonNull
    @Searchable
    private String name;

    @Indexed
    @NonNull
    private OrganizationType type;

    @Indexed
    @NonNull
    private OrganizationStatus status;

    @Indexed
    @NonNull
    private String supervisorId;

    private String url;

    @Searchable
    private String address;

    @Searchable
    private String city;

    @Indexed
//    @NonNull
    private String state;

    @Indexed
    private String zipcode;

    @Indexed
    private Point geoCoords;

    @NonNull
    @Searchable
    private String description;

    @Indexed
    @NonNull
    private Set<String> tags;

    private String photo;

    @Indexed
    private Double rating;

    //key is the name of the social; value is the link. ex: "Twitter" : "twitter.com/someAccount"
    private Map<String,String> socials;

    @NonNull
    private LocalDate activityDate;

    @NonNull
    private String username;

    enum OrganizationType{
        CHURCH,
        NON_PROFIT,
        CIVIC,
        OTHER;
    }

    enum OrganizationStatus {
        NEW,
        AWAITING_SIGNATURE,
        ACTIVE,
        INACTIVE,
        DENIED;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization organization = (Organization) o;

        return Objects.equals(name, organization.name) && Objects.equals(address, organization.address) &&
                Objects.equals(city, organization.city) && Objects.equals(state, organization.state) &&
                Objects.equals(zipcode, organization.zipcode);
    }

    @Override
    public int hashCode(){
        return Objects.hash(name,address,city,state,zipcode);
    }
}
