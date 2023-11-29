package com.example.backend.app.Business;

import com.example.backend.app.Business.DTO.SearchFilters;
import com.example.backend.app.Business.DTO.SearchRequest;
import com.example.backend.app.Business.DTO.UpdateBusinessDetailsRequest;
import com.example.backend.app.Category.Category;
import com.example.backend.app.Review.Review;
import com.example.backend.app.Review.ReviewRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.search.engine.spatial.DistanceUnit;
import org.hibernate.search.engine.spatial.GeoPoint;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessService {

    private final BusinessRepository businessRepository;

    private final EntityManager em;
    private final ReviewRepository reviewRepository;

    public Business createBusiness(String email, String name, String phoneNumber, String firebaseToken) throws FirebaseAuthException {
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(firebaseToken);
        Business business = new Business(email, name, phoneNumber, decodedToken.getUid());
        businessRepository.save(business);
        return business;
    }

    public Business findByFirebaseUid(String firebaseUid) {
        return businessRepository.findBusinessByFirebaseUid(firebaseUid).orElse(null);
    }

    public void recalculateBusinessScore(String businessId) {
        double score = reviewRepository.findAll().stream().mapToDouble(Review::getScore).average().orElse(0.0);
        Business business = businessRepository.findBusinessByFirebaseUid(businessId).orElseThrow();
        business.setScore(score != 0 ? score : null);
        businessRepository.save(business);
    }

    public void updateBusinessDetails(String businessId, UpdateBusinessDetailsRequest request) {
        Business business = businessRepository.findBusinessByFirebaseUid(businessId).orElse(null);
        if (business != null) {
            business.setDetails(
                    request.type(),
                    request.description(),
                    request.phoneNumber(),
                    request.categories(),
                    request.cost(),
                    request.attributes()
            );
            businessRepository.save(business);
        }
    }

    public List<Business> searchFuzzy(String searchQuery, SearchRequest request) {
        SearchSession searchSession = Search.session(em);
        SearchFilters filters = request.getFilters();

        var result = searchSession.search(Business.class)
                .where((f, root) -> {
                            root.add(f.match()
                                    .fields("name", "categories.name")
                                    .matching(searchQuery)
                                    .fuzzy(1, 3));
                            root.add(f.spatial().within().field("location")
                                    .circle(GeoPoint.of(filters.latitude(), filters.longitude()), filters.distance(), DistanceUnit.KILOMETERS));

                            if (filters.cost() != null) {
                                root.add(f.match().field("cost")
                                        .matching(filters.cost()));
                            }
                        }
                ).fetch(request.getPageNumber() * request.getPageSize(), request.getPageSize());

        return result.hits();
    }

    public List<String> autocomplete(String searchQuery) {
        SearchSession searchSession = Search.session(em);

        var businesses = searchSession.search(Business.class)
                .where(f -> f.wildcard()
                        .field("name")
                        .matching(searchQuery + "*"))
                .fetch(2);

        var categories = searchSession.search(Category.class)
                .where(f -> f.wildcard()
                        .field("name")
                        .matching(searchQuery + "*"))
                .fetch(5);

        List<String> result = new ArrayList<>();
        result.addAll(businesses.hits().stream().map(Business::getName).toList());
        result.addAll(categories.hits().stream().map(Category::getName).toList());

        return result;
    }


    public void reindex() throws InterruptedException {
        SearchSession searchSession = Search.session(em);

        MassIndexer indexer = searchSession.massIndexer(Business.class, Category.class)
                .threadsToLoadObjects( 7 );

        indexer.startAndWait();
    }

}
