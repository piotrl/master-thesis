package net.piotrl.music.modules.aggregation;

import lombok.extern.slf4j.Slf4j;
import net.piotrl.music.modules.account.Account;
import net.piotrl.music.modules.account.AccountRepository;
import net.piotrl.music.modules.aggregation.repository.AggregationMetadataCrudRepository;
import net.piotrl.music.modules.aggregation.repository.AggregationMetadataEntity;
import net.piotrl.music.modules.lastfm.aggregation.LastfmApiProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Component
public class AggregationScheduler {

    private final AggregationService aggregationService;
    private final AggregationMetadataCrudRepository metadataCrudRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public AggregationScheduler(AggregationService aggregationService,
                                AggregationMetadataCrudRepository metadataCrudRepository,
                                AccountRepository accountRepository) {
        this.aggregationService = aggregationService;
        this.metadataCrudRepository = metadataCrudRepository;
        this.accountRepository = accountRepository;
    }

    /**
     * Runs every day at 3am
     */
    @Scheduled(cron = "03 00 * * * *")
    public void start() {
        List<Account> accounts = accountRepository.findAll();
        Instant startTime = Instant.now();
        log.info("Scheduled aggregation started for {} users", accounts.size());

        for (Account account : accounts) {
            AggregationContext aggregationContext = buildContext(account);
            aggregationService.startAggregation(aggregationContext);
        }

        Instant finishTime = Instant.now();
        long aggregationTime = ChronoUnit.SECONDS.between(startTime, finishTime);
        log.info("Scheduled aggregation finished for {} users", accounts.size());
        log.info("Scheduled aggregation took {}s", aggregationTime);
    }

    private AggregationContext buildContext(Account account) {
        AggregationMetadataEntity metadata = metadataCrudRepository.findOne(account.getId());
        LastfmApiProperties lastfmApiProperties = new LastfmApiProperties(
                metadata.getLastfmUsername(), metadata.getLastfmApiKey(), metadata.getLastfmSecureKey()
        );

        AggregationContext context = new AggregationContext();
        context.setAccountId(account.getId());
        context.setRescuetimeApiKey(metadata.getRescuetimeApiKey());
        context.setLastfmProperties(lastfmApiProperties);

        return context;
    }
}
