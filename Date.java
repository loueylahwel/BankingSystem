import java.time.LocalDate;
public class Date {
    @SuppressWarnings("unused")
    private LocalDate date;
    int day, month, year;
    public Date(int day, int month, int year) {
        this.date = LocalDate.of(year, month, day);
        this.day = day;
        this.month = month;
        this.year = year;
    }
    public Date(){
        LocalDate currentDate = LocalDate.now();
        day = currentDate.getDayOfMonth();
        month = currentDate.getMonthValue();
        year = currentDate.getYear();
    }
}
