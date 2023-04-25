import static org.junit.Assert.*;
import org.junit.Test;
import java.time.LocalDate;
import java.util.*;

public class CenterTest {

    @Test
    public void testCreateLessonSchedule() {
        List<LocalDate> schedule = Center.createLessonSchedule(LocalDate.of(2023, 5, 1));
        assertEquals(16, schedule.size()); // each lesson should occur 16 times in the schedule
        assertEquals(LocalDate.of(2023, 5, 1), schedule.get(0)); // the first occurrence should be on May 1, 2023
        assertEquals(LocalDate.of(2023, 5, 8), schedule.get(2)); // the third occurrence should be on May 8, 2023
    }

    @Test
    public void testGenerateMonthlyReport() {
        Scanner scanner = new Scanner("5\n");
        Center.generateMonthlyReport(scanner);
        // TODO: Add more specific assertions based on expected output
    }

    @Test
    public void testGetNumCustomers() {
        Center.BOOKINGS.put("Alice", Arrays.asList(
            new Center.Booking("Zumba", LocalDate.of(2023, 5, 1), 4),
            new Center.Booking("Yoga", LocalDate.of(2023, 5, 1), 3),
            new Center.Booking("BoxFit", LocalDate.of(2023, 5, 8), 2)
        ));
        Center.BOOKINGS.put("Bob", Arrays.asList(
            new Center.Booking("Zumba", LocalDate.of(2023, 5, 8), 1),
            new Center.Booking("Yoga", LocalDate.of(2023, 5, 8), 5),
            new Center.Booking("BoxFit", LocalDate.of(2023, 5, 15), 3)
        ));
        assertEquals(5, Center.getNumCustomers("Yoga", LocalDate.of(2023, 5, 1))); // there are 5 customers booked for Yoga on May 1, 2023
        assertEquals(3, Center.getNumCustomers("BoxFit", LocalDate.of(2023, 5, 15))); // there are 3 customers booked for BoxFit on May 15, 2023
        assertEquals(0, Center.getNumCustomers("Bodysculpt", LocalDate.of(2023, 5, 22))); // there are no customers booked for Bodysculpt on May 22, 2023
    }
}
