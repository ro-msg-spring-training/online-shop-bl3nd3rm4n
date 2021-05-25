package ro.msg.learning.shop.utils;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.models.Location;
import ro.msg.learning.shop.models.Revenue;
import ro.msg.learning.shop.repositories.LocationRepository;
import ro.msg.learning.shop.repositories.PurchaseDetailRepository;
import ro.msg.learning.shop.repositories.PurchaseRepository;
import ro.msg.learning.shop.repositories.RevenueRepository;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Component
@AllArgsConstructor
public class ScheduledRevenueCalculator {

    private static final Logger log = LoggerFactory.getLogger(ScheduledRevenueCalculator.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private final PurchaseRepository purchaseRepository;
    private final PurchaseDetailRepository purchaseDetailRepository;
    private final RevenueRepository revenueRepository;
    private final LocationRepository locationRepository;


    @Scheduled(cron = "0 0 0 * * *")
    public void reportCurrentTime() {
        List<Location> locations = locationRepository.findAll();
        List<Revenue> revenues = revenueRepository.findAll();
        for (Location location : locations) {
            boolean found = false;
            for (Revenue revenue : revenues) {
                if (revenue.getLocation().equals(location)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                revenues.add(new Revenue(0, location, LocalDateTime.of(2000, 1, 1, 0, 0), new BigDecimal(0)));
            }
        }

        for (Revenue revenue : revenues) {
            revenue.setSum(revenue.getSum().add(
                    purchaseDetailRepository
                            .findAllByPurchaseInAndShippedFrom(purchaseRepository.findAllByCreatedAtAfter(
                                    revenue.getDate()), revenue.getLocation()).stream()
                            .map(purchaseDetail -> purchaseDetail.getProduct().getPrice()
                                    .multiply(BigDecimal.valueOf(purchaseDetail.getQuantity())))
                            .reduce(BigDecimal::add).orElse(BigDecimal.ZERO)));
            revenue.setDate(LocalDateTime.now());
        }
        revenueRepository.saveAll(revenues);

        log.info("The revenue has been updated {}", dateFormat.format(new Date()));
    }
}