package com.example.backend.app.Business;

import com.example.backend.app.Auth.Role;
import com.example.backend.app.Business.DTO.BusinessResponse;
import com.example.backend.app.Business.DTO.Location;
import com.example.backend.app.Business.DTO.SearchRequest;
import com.example.backend.app.Business.DTO.SearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/businesses")
@RequiredArgsConstructor
public class BusinessController {

    private final BusinessService businessService;

    @GetMapping("/{businessId}")
    BusinessResponse getBusinessById(@PathVariable String businessId) {
        Business business = businessService.findByFirebaseUid(businessId);
        return new BusinessResponse(
                business.getFirebaseUid(),
                business.getName(),
                business.getEmail(),
                business.getPhoneNumber(),
                business.getProfilePictureUrl(),
                business.getDescription(),
                business.getCuisine(),
                business.getCost(),
                business.getScore(),
                new Location(
                        business.getCountry(),
                        business.getAddress(),
                        business.getZipCode(),
                        business.getCity(),
                        business.getDistrict(),
                        business.getLocationLongitude(),
                        business.getLocationLatitude()
                ),
                business.getJoinDate(),
                Role.BUSINESS
        );
    }

    @GetMapping("/search/autocomplete")
    List<String> autocomplete(@RequestParam String searchQuery) {
        return businessService.autocomplete(searchQuery);
    }

    @PostMapping("/search")
    SearchResponse<BusinessResponse> searchBusinessesFuzzy(@RequestParam String searchQuery, @RequestBody SearchRequest request) {
        List<Business> businesses = businessService.searchFuzzy(searchQuery, request);
        List<BusinessResponse> businessList = businesses.stream().map(business -> new BusinessResponse(
                        business.getFirebaseUid(),
                        business.getName(),
                        business.getEmail(),
                        business.getPhoneNumber(),
                        business.getProfilePictureUrl(),
                        business.getDescription(),
                        business.getCuisine(),
                        business.getCost(),
                        business.getScore(),
                        new Location(
                                business.getCountry(),
                                business.getAddress(),
                                business.getZipCode(),
                                business.getCity(),
                                business.getDistrict(),
                                business.getLocationLongitude(),
                                business.getLocationLatitude()
                        ),
                        business.getJoinDate(),
                        Role.BUSINESS
                )
        ).toList();

        return new SearchResponse<>(request.getPageSize(), request.getPageNumber(), businessList);
    }

    @GetMapping("/reindex")
    void reindex() throws InterruptedException {
        businessService.reindex();
    }

}
