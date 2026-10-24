package com.mrthaw.inventory_backend;

import com.mrthaw.inventory_backend.common.enum_constant.RecordStatus;
import com.mrthaw.inventory_backend.common.util.PageResult;
import com.mrthaw.inventory_backend.master_data.business_partner.dto.BusinessPartnerResponseDto;
import com.mrthaw.inventory_backend.master_data.business_partner.dto.BusinessPartnerSearchRequestDto;
import com.mrthaw.inventory_backend.master_data.business_partner.entity.BusinessPartner;
import com.mrthaw.inventory_backend.master_data.business_partner.repo.BusinessPartnerRepo;
import com.mrthaw.inventory_backend.master_data.business_partner.service.BusinessPartnerServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
//@Transactional
public class BusinessPartnerIntegrationTest {

    @Autowired
    BusinessPartnerRepo businessPartnerRepo;

    @Autowired
    BusinessPartnerServiceImpl businessPartnerService;

    //@BeforeEach
    void setUp1() {
        businessPartnerRepo.deleteAll();

        List<BusinessPartner> businessPartners = new ArrayList<>();
        BusinessPartner bp1 = BusinessPartner.builder()
                .code("YTM")
                .name("YTM company ltd")
                .isCustomer(true)
                .status(RecordStatus.ACTIVE.getCode())
                .build();

        businessPartners.add(bp1);

        BusinessPartner bp2 = BusinessPartner.builder()
                .code("ATM")
                .name("ATM company ltd")
                .isCustomer(true)
                .status(RecordStatus.INACTIVE.getCode())
                .build();

        businessPartners.add(bp2);

        BusinessPartner bp3 = BusinessPartner.builder()
                .code("SYM")
                .name("SYM company ltd")
                .isCustomer(false)
                .status(RecordStatus.ACTIVE.getCode())
                .build();

        businessPartners.add(bp3);

        businessPartnerRepo.saveAll(businessPartners);
    }

    @BeforeEach
    void setUp() {

        businessPartnerRepo.deleteAll();

        List<BusinessPartner> businessPartners = new ArrayList<>();
        int maxBatchSize = 10;

        // Predefined arrays for random generation
        String[] prefixes = {"SYM", "ABC", "XYZ", "GLOBAL", "PRIME"};
        String[] streets = {"Main Road", "Merchant Street", "Pyay Road", "Insein Road", "Anawrahta Street"};
        String[] cities = {"Yangon", "Mandalay", "Naypyitaw", "Bago", "Taunggyi"};

        for (int x = 0; x < 50; x++) {
            // 1. Random Code & Name generation
            String randomPrefix = prefixes[ThreadLocalRandom.current().nextInt(prefixes.length)];
            String randomSuffix = UUID.randomUUID().toString().substring(0, 6).toUpperCase();

            String code = randomPrefix + "-" + randomSuffix;
            String name = randomPrefix + " " + randomSuffix + " Company Ltd";

            // 2. Random Address generation
            String randomStreet = streets[ThreadLocalRandom.current().nextInt(streets.length)];
            String randomCity = cities[ThreadLocalRandom.current().nextInt(cities.length)];
            int streetNumber = ThreadLocalRandom.current().nextInt(1, 150);
            String address = "No. " + streetNumber + ", " + randomStreet + ", " + randomCity;

            // 3. Alternate status between integer 1 and 2
            int status = (x % 2 == 0) ? 1 : 2;

            BusinessPartner bp = BusinessPartner.builder()
                    .code(code)
                    .name(name)
                    .address(address)
                    .isCustomer(x % 2 == 0)
                    .status(status)
                    .build();

            businessPartners.add(bp);

            // Save in batches of 10
            if (businessPartners.size() == maxBatchSize) {
                businessPartnerRepo.saveAll(businessPartners);
                businessPartners.clear(); // Clears the list for the next batch
            }
        }

        // Save any remaining items if total isn't a clean multiple of batch size
        if (!businessPartners.isEmpty()) {
            businessPartnerRepo.saveAll(businessPartners);
        }
    }

    @Test
    public void return_rows_on_search() {
        BusinessPartnerSearchRequestDto searchRequestDto = new BusinessPartnerSearchRequestDto();
        searchRequestDto.setCode("YTM");
        //searchRequestDto.setStatus(RecordStatus.INACTIVE.getCode());
        //searchRequestDto.setCustomer(false);

        PageResult<BusinessPartnerResponseDto> pageResult = businessPartnerService.searchBusinessPartners(searchRequestDto);

        assertThat(pageResult.getList()).hasSize(20);

        BusinessPartnerResponseDto businessPartnerResponseDto = pageResult.getList().get(0);

        //assertThat(businessPartnerResponseDto.getCode()).isEqualTo("ATM");
        //assertThat(businessPartnerResponseDto.getName()).isEqualTo("ATM company ltd");
        //assertThat(businessPartnerResponseDto.getIsCustomer()).isTrue();
    }
}
