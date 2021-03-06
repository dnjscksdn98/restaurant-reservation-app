package com.alexcode.eatgo.interfaces;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alexcode.eatgo.application.ReviewService;
import com.alexcode.eatgo.domain.models.Review;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private ReviewService reviewService;

  @Test
  public void list() throws Exception {
    List<Review> reviews = new ArrayList<>();
//    reviews.add(Review.builder().description("Cool").build());

//    given(reviewService.getReviews()).willReturn(reviews);

    mvc.perform(get("/reviews"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Cool")));
  }
}