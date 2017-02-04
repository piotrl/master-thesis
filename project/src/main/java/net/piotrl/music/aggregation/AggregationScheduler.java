package net.piotrl.music.aggregation;

import lombok.extern.slf4j.Slf4j;
import net.piotrl.music.account.Account;
import net.piotrl.music.account.AccountRepository;
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
    private final AccountRepository accountRepository;

    @Autowired
    public AggregationScheduler(AggregationService aggregationService, AccountRepository accountRepository) {
        this.aggregationService = aggregationService;
        this.accountRepository = accountRepository;
    }

    /**
     * Runs every day at 3am
     */
    @Scheduled(cron = "03 00 * * *")
    public void start() {
        List<Account> accounts = accountRepository.findAll();
        Instant startTime = Instant.now();
        log.info("Scheduled aggregation started for {} users", accounts.size());

        for (Account account : accounts) {
            aggregationService.startAggregation(account);
        }

        Instant finishTime = Instant.now();
        long aggregationTime = ChronoUnit.SECONDS.between(startTime, finishTime);
        log.info("Scheduled aggregation finished for {} users", accounts.size());
        log.info("Scheduled aggregation took {}s", aggregationTime);
    }
}
