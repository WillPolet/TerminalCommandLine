import java.io.FileReader;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import com.opencsv.CSVReader;


class MonthExtractor {
    public static Month extractMonth(String dateString) {
        // Define the date format to match your input format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            // Parse the input string into a LocalDate object
            LocalDate date = LocalDate.parse(dateString, formatter);

            // Extract the month from the LocalDate
            return date.getMonth();
        } catch (Exception e) {
            // Handle parsing exceptions (e.g., invalid format)
            throw new IllegalArgumentException("Invalid date format or date: " + dateString, e);
        }
    }
}

class TradeData {
    private String Direction;
    private int Year;
    private String Date;
    private String Weekday;
    private String Country;
    private String Commodity;
    private String Transport_Mode;
    private String Measure;
    private long Value;
    private long Cumulative;

    public TradeData(String Direction, int Year, String Date, String Weekday, String Country,
                     String Commodity, String Transport_Mode, String Measure, long Value, long Cumulative) {
        this.Direction = Direction;
        this.Year = Year;
        this.Date = Date;
        this.Weekday = Weekday;
        this.Country = Country;
        this.Commodity = Commodity;
        this.Transport_Mode = Transport_Mode;
        this.Measure = Measure;
        this.Value = Value;
        this.Cumulative = Cumulative;
    }

    public void print(){
        System.out.println(Direction + " " + Year + " " + Date + " " + Weekday + " " + Country + " " + Commodity + " " + Transport_Mode
                + " " + Measure + " " + Value + " " + Cumulative);
    }
    public String getDate() {
        return Date;
    }

    public int getYear() {
        return Year;
    }

    public String getCommodity() {
        return Commodity;
    }

    public String getCountry() {
        return Country;
    }

    public String getDirection() {
        return Direction;
    }

    public long getCumulative() {
        return Cumulative;
    }

    public long getValue() {
        return Value;
    }

    public String getMeasure() {
        return Measure;
    }

    public String getTransport_Mode() {
        return Transport_Mode;
    }

    public String getWeekday() {
        return Weekday;
    }

    public void setMeasure(String Measure){
        this.Measure = Measure;
    }

    public void setValue(long Value){
        this.Value = Value;
    }
}




public class CSVRead {
    public static List<TradeData> toObject() {
        String csvFilePath = "C:\\Users\\willi\\Desktop\\JavaBeCode\\WeekThree\\src\\main\\resources\\covid_and_trade.csv";
        List<TradeData> tradeDataList = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            String[] headers = reader.readNext(); // Read the header line

            tradeDataList = new ArrayList<>();


            String[] line;
            while ((line = reader.readNext()) != null) {
                TradeData tradeData = new TradeData(
                        line[0], Integer.parseInt(line[1]), line[2],
                        line[3], line[4], line[5], line[6], line[7],
                        Long.parseLong(line[8]), Long.parseLong(line[9])
                );
                tradeDataList.add(tradeData);
            }

            return tradeDataList;

        } catch (Exception e) {
            e.printStackTrace();

        }
        return tradeDataList;
    }

    public static String[] monthlyTotal(int Month, int Year, String Country, String Commodity, String Transport_Mode, String Measure){
        List<TradeData> dataList = CSVRead.toObject();
        List<TradeData> filteredExports = dataList.stream()
                .filter(tradeData -> Objects.equals(tradeData.getDirection(), "Exports"))
                .filter(tradeData -> tradeData.getYear() == Year) // works
                .filter(tradeData -> Objects.equals(tradeData.getCountry(), Country))
                .filter(tradeData -> Objects.equals(tradeData.getCommodity(), Commodity))
                .filter(tradeData -> Objects.equals(tradeData.getTransport_Mode(), Transport_Mode))
                .filter(tradeData -> Objects.equals(tradeData.getMeasure(), Measure))
                .collect(Collectors.toList());

        List<TradeData> filteredImports = dataList.stream()
                .filter(tradeData -> Objects.equals(tradeData.getDirection(), "Imports"))
                .filter(tradeData -> tradeData.getYear() == Year) // works
                .filter(tradeData -> Objects.equals(tradeData.getCountry(), Country))
                .filter(tradeData -> Objects.equals(tradeData.getCommodity(), Commodity))
                .filter(tradeData -> Objects.equals(tradeData.getTransport_Mode(), Transport_Mode))
                .filter(tradeData -> Objects.equals(tradeData.getMeasure(), Measure))
                .collect(Collectors.toList());

        Map<Month,Double> filteredExportsGroupByMonth = filteredExports.stream()
                .collect(Collectors.groupingBy(
                        tradeData -> MonthExtractor.extractMonth(tradeData.getDate()),
                        Collectors.summingDouble(TradeData::getValue)
                ));

        filteredExportsGroupByMonth.entrySet().stream()
                .sorted(Map.Entry.comparingByKey());// Sort by month

        Map<Month,Double> filteredImportGroupByMonth = filteredImports.stream()
                .collect(Collectors.groupingBy(
                        tradeData -> MonthExtractor.extractMonth(tradeData.getDate()),
                        Collectors.summingDouble(TradeData::getValue)
                ));

        filteredImportGroupByMonth.entrySet().stream()
                .sorted(Map.Entry.comparingByKey());// Sort by month

        String month = getMonth.name(Month).toString();
        String year = Integer.toString(Year);
        String valueExport = filteredExportsGroupByMonth.get(getMonth.name(Month)).toString();
        String valueImport = filteredImportGroupByMonth.get(getMonth.name(Month)).toString();

        String [] values = {month, year, valueImport, valueExport};

        return values;

//        System.out.println("Monthly total of exports of" + getMonth.name(Month) + " " + Year + " : " + filteredExportsGroupByMonth.get(getMonth.name(Month)));
//        System.out.println("Monthly total of imports of" + getMonth.name(Month) + " " + Year + " : " + filteredImportGroupByMonth.get(getMonth.name(Month)));

    }

    public static String[] monthlyAverage(int Month, int Year, String Country, String Commodity, String Transport_Mode, String Measure){
        String [] values = monthlyTotal(Month, Year, Country, Commodity, Transport_Mode, Measure);
        String month = values[0];
        String year = values[1];
        String totalImports = values[2];
        String totalExports = values[3];
        int monthNumber = getMonth.number(month);
        int yearNumber = Integer.parseInt(year);
        double totalExportsDouble = Double.parseDouble(totalExports);
        double totalImportsDouble = Double.parseDouble(totalImports);

        // Check for numbers of day of

        YearMonth yearMonthObject = YearMonth.of(yearNumber,monthNumber);
        double daysInMonth = yearMonthObject.lengthOfMonth();

        double averageExport = totalExportsDouble/daysInMonth;
        double averageImport = totalImportsDouble/daysInMonth;

        // I need month, year and average

        return new String[]{month,year, Double.toString(averageImport), Double.toString(averageExport)};
//        System.out.println("Monthly total of exports of" + getMonth.name(Month) + " " + Year + " : " + filteredExportsGroupByMonth.get(getMonth.name(Month)));
//        System.out.println("Monthly total of imports of" + getMonth.name(Month) + " " + Year + " : " + filteredImportGroupByMonth.get(getMonth.name(Month)));

    }

    public static Double[] yearlyTotal(int Year, String Country, String Commodity, String Transport_Mode, String Measure, Boolean loop){
            double totalOfImport = 0;
            double totalOfExport = 0;
        for (int i = 1; i<13 ;i++){
            String [] values = monthlyTotal(i, Year, Country, Commodity, Transport_Mode, Measure);
            if(loop){
                System.out.println("Monthly total of exports of " + values[0] + " " + values[1] + " : " + values[3]);
                System.out.println("Monthly total of imports of " + values[0] + " " + values[1] + " : " + values[2]);
            }
            totalOfImport += Double.parseDouble(values[2]);
            totalOfExport += Double.parseDouble(values[3]);
        }
        Double year = (double) Year;
        return new Double []{year, totalOfImport, totalOfExport};


    }

    public static void yearlyAverage(int Year, String Country, String Commodity, String Transport_Mode, String Measure){
        for (int i = 1; i < 13 ;i++){
            String [] values = monthlyAverage(i, Year, Country, Commodity, Transport_Mode, Measure);
            System.out.println("Monthly average of exports of " + values[0] + " " + values[1] + " : " + values[3]);
            System.out.println("Monthly average of imports of " + values[0] + " " + values[1] + " : " + values[2]);
        }
        Double [] yearTotalValues = yearlyTotal(Year,Country,Commodity, Transport_Mode, Measure, false);
        Double numberOfMonth = 12.0;
        Double averageOfYearImport = yearTotalValues[1]/numberOfMonth;
        Double averageOfYearExport = yearTotalValues[2]/numberOfMonth;
        System.out.println("Yearly average (imports) of year " + Year + " is : " + averageOfYearImport);
        System.out.println("Yearly average (exports) of year " + Year + " is : " + averageOfYearExport);
    }
}





class getMonth{
    public static Month name(int number){
        switch (number){
            case 1 -> {return Month.JANUARY;}
            case 2 -> {return Month.FEBRUARY;}
            case 3 -> {return Month.MARCH;}
            case 4 -> {return Month.APRIL;}
            case 5 -> {return Month.MAY;}
            case 6 -> {return Month.JUNE;}
            case 7 -> {return Month.JULY;}
            case 8 -> {return Month.AUGUST;}
            case 9 -> {return Month.SEPTEMBER;}
            case 10 -> {return Month.OCTOBER;}
            case 11 -> {return Month.NOVEMBER;}
            case 12 -> {return Month.DECEMBER;}
        }
        return null;
    }

    public static int number(String month){
        switch (month){
            case "JANUARY" -> {return 1;}
            case "FEBRUARY" -> {return 2;}
            case "MARCH" -> {return 3;}
            case "APRIL" -> {return 4;}
            case "MAY" -> {return 5;}
            case "JUNE" -> {return 6;}
            case "JULY" -> {return 7;}
            case "AUGUST" -> {return 8;}
            case "SEPTEMBER" -> {return 9;}
            case "OCTOBER" -> {return 10;}
            case "NOVEMBER" -> {return 11;}
            case "DECEMBER" -> {return 12;}
        }
        return 0;
    }
}



