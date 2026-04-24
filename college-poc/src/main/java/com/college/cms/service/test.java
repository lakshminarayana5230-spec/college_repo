package com.daimler.safira.scheduler;

import com.daimler.safira.repository.EcoSheetRepository;
import com.daimler.safira.repository.SafiraConfigurationRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "safira.scheduler.enabled", havingValue = "true", matchIfMissing = false)
@RequiredArgsConstructor
public class DataSyncScheduler {

    private static final Logger log = LoggerFactory.getLogger(DataSyncScheduler.class);

    private final EcoSheetRepository ecoSheetRepo;
    private final SafiraConfigurationRepository configRepo;

    @Scheduled(fixedRateString = "${safira.scheduler.eco-sync-rate-ms:300000}",
               initialDelayString = "${safira.scheduler.eco-sync-rate-ms:300000}")
    public void syncEcoStatus() {
        log.info("ECO status sync started — total ECOs: {}", ecoSheetRepo.count());
        // Placeholder: in production this would sync with external SAFIRA DB
        // or trigger MQ message processing
        log.info("ECO status sync completed");
    }

    @Scheduled(fixedRateString = "${safira.scheduler.kem-cache-rate-ms:60000}",
               initialDelayString = "${safira.scheduler.kem-cache-rate-ms:60000}")
    public void reloadKemCache() {
        log.debug("KEM cache reload check");
        // Placeholder: reload KEM cache from ext_rep tables
    }

    @Scheduled(cron = "0 0 0 * * *", zone = "Europe/Berlin")
    public void dailyMaintenance() {
        log.info("Daily maintenance started");
        // Placeholder: workflow name update, label area refresh
        log.info("Daily maintenance completed");
    }
}
