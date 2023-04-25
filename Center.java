//importing some libraries so that we can use some specific functions in the code
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
// this is the public class.
public class Center {
    private static final Map<String, List<Booking>> BOOKINGS = new HashMap<>();
    private static final Map<String, List<LocalDate>> TIMETABLE = createTimetable();
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    //these below are the prices of different classes, these are started from 0
    
    public static double Cost_of_Yoga = 0;
    public static double cost_of_Zumba = 0;
    public static double cost_of_BoxFit = 0;
    public static double cost_of_Bodysculpt = 0;
//This below is the main function from which all the code will start executing
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {    //this presents the condition for user so that he can choose option from the menu to perform desired actions
            System.out.println("Our Weekend Fitness Club system welcomes you! There are some choices below, you can choose one of them at one time and get the answers.");
            System.out.println("1. Timetable viewing"); //this will allow to view timetable
            System.out.println("2. Make the Bookings"); //this will allow to make the bookings
            System.out.println("3. Cancel the Booking made"); //this will allow to cancel the bookings made
            System.out.println("4. View the Bookings made"); //this will allow to view the made bookings
            System.out.println("5. Attend the Lessons"); //this will allow to attend the lesson
            System.out.println("6. Generate Average Rating Report ");//this will allow to generate the average rating report
            System.out.println("7. Generate the monthly Champion Report");//this will generate the monthly report
            System.out.println("8. Exit");//this will allow tehuser
//this below line of code will help to take input from the user about the choice
            int choice = scanner.nextInt();
            scanner.nextLine(); // add newline

            switch (choice) { //allow the user to switch the choice
                case 1:
                    viewTimetable();
                    break;
                case 2:
                    makeBooking(scanner);
                    break;
                case 3:
                    cancelBooking(scanner);
                    break;
                case 4:
                    viewBookings();
                    break;
                case 5:
                    attendLesson(scanner);
                    break;
                case 6:
                    generateMonthlyReport(scanner);
                    break;
                case 7:
                    generateChampionReport();
                    break;
                case 8:
                    System.out.println("Its sad to know that you are leaving i hope you felt nice with our system");
                    return;
                default:
                    System.out.println("I think you made the wrong choice, Kindly provide an inout from 1 to 8");
            }
        }
    }

    private static Map<String, List<LocalDate>> createTimetable() {
        Map<String, List<LocalDate>> timetable = new HashMap<>();
        timetable.put("Zumba", createLessonSchedule(LocalDate.of(2023, 5, 1)));
        timetable.put("Yoga", createLessonSchedule(LocalDate.of(2023, 5, 8)));
        timetable.put("Bodysculpt", createLessonSchedule(LocalDate.of(2023, 5, 15)));
        timetable.put("BoxFit", createLessonSchedule(LocalDate.of(2023, 5, 22)));
        return timetable;
    }

    private static List<LocalDate> createLessonSchedule(LocalDate startDate) {
        List<LocalDate> schedule = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            schedule.add(startDate.plusDays(i));
            schedule.add(startDate.plusDays(i).plusWeeks(1));
        }
        return schedule; //this will return the schedule
    }
    //this below is a method which is used to generate the monthly report
    private static void generateMonthlyReport(Scanner scanner) {
        System.out.print("Enter a number for the month (5 for may)");
        String monthString = scanner.nextLine();
        int month;
        try {
            month = Integer.parseInt(monthString);
        } catch (Exception e) {
            System.out.println("You entered the wrong month number. Kindly enter after checking the timetable please");
            return;
        }
        LocalDate startDate = LocalDate.of(2023, month, 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);
        System.out.println("Monthly report for " + startDate.getMonth().name() + ":");
        for (String lesson : TIMETABLE.keySet()) {
            System.out.println(lesson + ":");
            for (LocalDate date : TIMETABLE.get(lesson)) {
                if (date.isAfter(endDate)) {
                    break;
                }
                if (date.getMonth() == startDate.getMonth()) {
                    int customers = getNumCustomers(lesson, date);
                    double rating = getAvgRating(lesson, date);
                    System.out.printf("%s: %d customers, %.2f average rating%n", date.format(DATE_FORMATTER), customers, rating);
                }
            }
            //this below is used to add a new line
            System.out.println(); 
        }
    }
    
    private static int getNumCustomers(String lesson, LocalDate date) {
        //initializing the counting variable from 0
        int counting = 0;
        for (List<Booking> bookings : BOOKINGS.values()) {
            for (Booking booking : bookings) {
                if (booking.getLesson().equals(lesson) && booking.getDate().equals(date)) {
                    counting++;
                }
            }
        }
        return counting;
    }
   //this below method is used to get the avg rating of all the lessons 
    private static double getAvgRating(String lesson, LocalDate date) {
        double add = 0;
        int counting = 0;
        for (List<Booking> bookings : BOOKINGS.values()) {
            for (Booking booking : bookings) {
                if (booking.getLesson().equals(lesson) && booking.getDate().equals(date)) {
                    add += booking.getRating();
                    counting++;
                }
            }
        }
        if (counting == 0) {
            return 0;
        }
        return add / counting;
    }
    

    private static void viewTimetable() {
        System.out.println("Showing the timetable for the next 8 weekends");
        for (String lesson : TIMETABLE.keySet()) {
            System.out.println(lesson + ":");
            for (LocalDate date : TIMETABLE.get(lesson)) {
                System.out.println("- " + date.format(DATE_FORMATTER));
            }
            System.out.println(); // add a blank line between lessons
        }
        System.out.print(" Yoga price  :- 10 dollars \n Zumba price :- 20 dollars \n BoxFit price :- 50 dollars \n Bodysculpt price :- 15 dollars \n");
    }

    private static void makeBooking(Scanner scanner) {
        System.out.print("Please enter your first name ");
        String name = scanner.nextLine();

        System.out.println("These are the available lessons");
        for (String lesson : TIMETABLE.keySet()) {
            System.out.println("- " + lesson);
        }
        System.out.print("Enter the name of the lesson that you want to book");
        String lesson = scanner.nextLine();
        if (!TIMETABLE.containsKey(lesson)) {
            System.out.println("You mistakenly entered the wrong lesson. Please try again");
            return;
        }

        System.out.print("Enter the date you want to book in the format of (dd/MM/yyyy): ");
        String dateString = scanner.nextLine();
        LocalDate date;
        try {
            date = LocalDate.parse(dateString, DATE_FORMATTER);
        } catch (Exception e) {
            System.out.println("You entered the date in the wrong format. Please try again with he right format and date");
            return;
        }
    
        if (!TIMETABLE.get(lesson).contains(date)) {
            System.out.println("Sorry! :( This lesson isn't available for this date i hope you can understand");
            return;
        }
    
        Booking booking = new Booking(name, lesson, date);
    
        if (!BOOKINGS.containsKey(name)) {
            BOOKINGS.put(name, new ArrayList<>());
        }
        List<Booking> userBookings = BOOKINGS.get(name);
        if (userBookings.contains(booking)) {
            System.out.println("But you have booked a lesson on this date. sorry");
            return;
        }
        userBookings.add(booking);
        System.out.println("Booking is succesful. Congrats");
        
        if (lesson.equals("Yoga")){
            Cost_of_Yoga = Cost_of_Yoga + 10;
            
        }
        else if (lesson.equals("Zumba")){
            cost_of_Zumba = cost_of_Zumba + 20;
        }
        else if (lesson.equals("BoxFit")){
            cost_of_BoxFit = cost_of_BoxFit + 50;
        }
        else if (lesson.equals("Bodysculpt")){
            cost_of_Bodysculpt = cost_of_Bodysculpt + 15;
        }
        
    }
// this below method is used to generate the monthly champion lesson
    private static void generateChampionReport(){
        
        if (Cost_of_Yoga < cost_of_Zumba) {
            if(cost_of_BoxFit < cost_of_Zumba){
                if(cost_of_Bodysculpt < cost_of_Zumba){
                    System.out.println("The most elevated pay creating lesson is ZUMBA.");
                }
            }
            
        } 

        if (cost_of_Zumba < Cost_of_Yoga) {
            if(cost_of_BoxFit < Cost_of_Yoga){
                if(cost_of_Bodysculpt < Cost_of_Yoga){
                    System.out.println("The most elevated pay creating lesson is Yoga.");
                }
            }
        } 

        if (cost_of_Zumba < cost_of_BoxFit) {
            if(Cost_of_Yoga < cost_of_BoxFit){
                if(cost_of_Bodysculpt < cost_of_BoxFit){
                    System.out.println("The most elevated pay creating lesson is BoxFit.");
                }
            }
        } 
        if (cost_of_Zumba < cost_of_Bodysculpt) {
            if(cost_of_BoxFit < cost_of_Bodysculpt){
                if(Cost_of_Yoga < cost_of_Bodysculpt){
                    System.out.println("The most elevated pay creating lesson is BodySculpt.");
                }
            }
        } 

        else {
            System.out.println("There are 0 bookings made by the customer so the earning of each lesson is zero");
        }
        }
    
    
    private static void cancelBooking(Scanner scanner) {
        System.out.print("Please enter the firstname you entered while making the booking: ");
        String name = scanner.nextLine();
    
        if (!BOOKINGS.containsKey(name)) {
            System.out.println("No booking found. sorry.");
            return;
        }
    
        List<Booking> userBookings = BOOKINGS.get(name);
        if (userBookings.isEmpty()) {
            System.out.println("No booking found. sorry");
            return;
        }
    
        System.out.println("Bookings made by you:-"); //print the message for the user
        for (int i = 0; i < userBookings.size(); i++) {//initiate the loop
            Booking booking = userBookings.get(i);
            System.out.printf("%d. %s on %s%n", i + 1, booking.getLesson(), booking.getDate().format(DATE_FORMATTER));
        }
    
        System.out.print("Enter the number of that particular booking that you want to cancel");
        int bookingNumber = scanner.nextInt();
        scanner.nextLine(); // consume newline
    
        if (bookingNumber < 1 || bookingNumber > userBookings.size()) {
            System.out.println("Invalid number is entered by you");
            return;
        }
    
        Booking bookingToRemove = userBookings.get(bookingNumber - 1);
        userBookings.remove(bookingToRemove);
    
        System.out.println("Booking cancelled.");
    }
    
    private static void viewBookings() {
        System.out.println("Bookings:");
        for (String name : BOOKINGS.keySet()) {
            System.out.println(name + ":");
            List<Booking> userBookings = BOOKINGS.get(name);
            for (Booking booking : userBookings) {
                System.out.printf("- %s: %s on %s%n", name, booking.getLesson(), booking.getDate().format(DATE_FORMATTER));
            }
            System.out.println(); // add a blank line between users
        }
    }
    
    private static class Booking {
        private final String name;
        private final String lesson;
        private final LocalDate date;
    
        public Booking(String name, String lesson, LocalDate date) {
            this.name = name;
            this.lesson = lesson;
            this.date = date;
        }
        int rating;
        public void setRating(int rating) {
            this.rating = rating;
        }
     
        public int getRating() {
            if (this.rating == 0) {
                System.out.println("The rating of this booking is not done yet");
                return 0;
            } else {
                return this.rating;
            }
        }
        
        public String getName() {
            return name;
        }
    
        public String getLesson() {
            return lesson;
        }
    
        public LocalDate getDate() {
            return date;
        }
    
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Booking)) return false;
            Booking booking = (Booking) o;
            return name.equals(booking.name) && lesson.equals(booking.lesson) && date.equals(booking.date);
        }
    
        @Override
        public int hashCode() {
            return Objects.hash(name, lesson, date);
        }
    }
    private static void attendLesson(Scanner scanner) {
        System.out.print("Please enter your name, the one you entered while making the booking: ");
        String name = scanner.nextLine();
        if (!BOOKINGS.containsKey(name)) {
            System.out.println("We don't have any bookings from this name.");
            return;
        }
        List<Booking> userBookings = BOOKINGS.get(name);
        if (userBookings.isEmpty()) {
            System.out.println("We don't have any bookings from this name");
            return;
        }
        System.out.println("Your bookings:");
        for (int i = 0; i < userBookings.size(); i++) {
            Booking booking = userBookings.get(i);
            System.out.printf("%d. %s on %s%n", i + 1, booking.getLesson(), booking.getDate().format(DATE_FORMATTER));
        }
        System.out.print("Is the lesson is attended by you ??? (y/n) "); //ask the user to rate the class
        String answer = scanner.nextLine();
        if (Objects.equals(answer, "y")) {
            System.out.print("Rate the class on the scale of  1 to 5: ");
            int rating = scanner.nextInt();
            scanner.nextLine(); // consume newline
            for (Booking booking : userBookings) {
                booking.setRating(rating);
            }
        } else if (Objects.equals(answer, "n")) { //put the condition to join the class before rating
            System.out.println("Please join the class in order to rate the class");
        } else {
            System.out.println("Invalid choice is entered by you");
        }
    }
    
}