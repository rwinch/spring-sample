package example;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

import org.junit.jupiter.api.Test;

public class TheTests {

	@Test
	void go() {
		Instant now = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
		System.out.println(now);
	}
}
