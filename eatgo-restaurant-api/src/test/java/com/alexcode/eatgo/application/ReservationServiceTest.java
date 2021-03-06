package com.alexcode.eatgo.application;

import com.alexcode.eatgo.domain.repository.ReservationRepository;
import com.alexcode.eatgo.network.SuccessResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

class ReservationServiceTest {

    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        reservationService = new ReservationService(reservationRepository);
    }

    @Test
    public void list() {
        Long restaurantId = 1L;
        SuccessResponse response = reservationService.list(restaurantId);

        verify(reservationRepository).findAllByRestaurantId(restaurantId);
    }

}