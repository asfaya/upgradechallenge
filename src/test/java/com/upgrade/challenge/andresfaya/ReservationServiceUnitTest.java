package com.upgrade.challenge.andresfaya;

import com.upgrade.challenge.andresfaya.exceptions.ReservationNotCreatedException;
import com.upgrade.challenge.andresfaya.models.Reservation;
import com.upgrade.challenge.andresfaya.services.ReservationService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChallengeApplication.class)
@TestPropertySource(locations = "classpath:application.properties")
public class ReservationServiceUnitTest {

    @Autowired
    private ReservationService reservationService;

    @Test
    @Sql({"/test-data.sql"})
    public void multipleConcurrent_onlyOneSucceeds() throws InterruptedException, ExecutionException {
        Collection<Callable<Reservation>> tasks = new ArrayList<>();

        LocalDateTime beginDate = LocalDate.now().plusDays(1).atTime(12, 0, 0);
        LocalDateTime endDate = beginDate.plusDays(1);

        Reservation dummyReservation = getNewReservation(beginDate, endDate);

        for (int i = 0; i < 100; i++) {
            ((ArrayList<Callable<Reservation>>) tasks).add(new Task(dummyReservation));
            dummyReservation = getNewReservation(beginDate, endDate);
        }

        int numThreads = 100;  // 10 threads
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        List<Future<Reservation>> results = executor.invokeAll(tasks);

        int succeeded = 0, failed = 0;

        for(Future<Reservation> result : results){
            try {
                Reservation reservation = result.get();
                succeeded++;
            }
            catch (Exception ex) {
                failed++;
            }
        }
        executor.shutdown(); //always reclaim resources

        Assert.assertEquals(1, succeeded);
        Assert.assertEquals(99, failed);
    }

    @Test
    @Sql({"/test-data.sql"})
    public void multipleConcurrent_withDifferentDates_allSucceed() throws InterruptedException, ExecutionException {
        Collection<Callable<Reservation>> tasks = new ArrayList<>();

        LocalDateTime beginDate = LocalDate.now().plusDays(1).atTime(12, 0, 0);
        LocalDateTime endDate = beginDate.plusDays(1);

        Reservation dummyReservation = getNewReservation(beginDate, endDate);

        for (int i = 0; i < 100; i++) {
            ((ArrayList<Callable<Reservation>>) tasks).add(new Task(dummyReservation));
            beginDate = dummyReservation.getBeginDate().plusDays(5);
            endDate = dummyReservation.getEndDate().plusDays(5);
            dummyReservation =  getNewReservation(beginDate, endDate);
        }

        int numThreads = 100;  // 10 threads
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        List<Future<Reservation>> results = executor.invokeAll(tasks);

        int succeeded = 0, failed = 0;

        for(Future<Reservation> result : results){
            try {
                Reservation reservation = result.get();
                succeeded++;
            }
            catch (Exception ex) {
                failed++;
            }
        }
        executor.shutdown(); //always reclaim resources

        Assert.assertEquals(100, succeeded);
        Assert.assertEquals(0, failed);
    }

    /** Try to add a reservation. Return true only if successful. */
    private final class Task implements Callable<Reservation> {
        Task(Reservation reservation){
            this.reservation = reservation;
        }
        /** Access a URL, and see if you get a healthy response. */
        @Override
        public Reservation call() throws ReservationNotCreatedException {
            return reservationService.create(reservation);
        }
        private final Reservation reservation;
    }

    private Reservation getNewReservation(LocalDateTime beginDate, LocalDateTime endDate) {
        Reservation reservation = new Reservation();
        reservation.setId(new Long(0));
        reservation.setBeginDate(beginDate);
        reservation.setEndDate(endDate);
        reservation.setFullName("John Smith");
        reservation.setEmail("john@smith.com");
        reservation.setReservationDate(LocalDateTime.now());

        return reservation;
    }
}
