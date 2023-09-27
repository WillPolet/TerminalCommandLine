import java.util.*;

import static java.lang.Integer.parseInt;

class Input{
    public static String Command(){
        Scanner sc= new Scanner(System.in);    //System.in is a standard input stream
        System.out.print("Enter command : ");
        String command = sc.nextLine();
        return command;
    }
}

class Which{
    public static void Command(String command){
        if (Objects.equals(command,"help")){
            Help.all();
            String [] args = new String[0];
            CleanProject.main(args);
        } else if (Objects.equals("monthly_total",command)) {
            Help.Command("monthly_total");
            String [] args = new String[0];
            CleanProject.main(args);
        } else if (Objects.equals("overview", command)){
            Overview.Command();
            String [] args = new String[0];
            CleanProject.main(args);
        } else if (Objects.equals("monthly_average", command)) {
            Help.Command("monthly_average");
            String [] args = new String[0];
            CleanProject.main(args);
        }

        if (command.contains("help ")){
            int indexOfSpace = command.indexOf(' ');
            String secondPart = command.substring(indexOfSpace + 1);
            Help.Command(secondPart);
            String [] args = new String[0];
            CleanProject.main(args);
        }
        if (command.contains("monthly_total ")){
            Monthly.Total(command);
            String [] args = new String[0];
            CleanProject.main(args);
        }
        if (command.contains("monthly_average ")){
            Monthly.average(command);
            String [] args = new String[0];
            CleanProject.main(args);
        }
        if (command.contains("yearly_total")){
            Yearly.Total(command);
            String [] args = new String[0];
            CleanProject.main(args);
        }
        if (command.contains("yearly_average ")){
            Yearly.Average(command);
            String [] args = new String[0];
            CleanProject.main(args);
        }
    }
}

class Help {
    public static void all(){
        System.out.println("help: Returns a list of available commands with a brief description.");
        System.out.println("help <command>: Provides a full explanation of what the \"\" does and the parameters it requires.");
        System.out.println("monthly_total: Returns the sum of both the export and import for a specified month of a specified year.");
        System.out.println("monthly_average: Returns the average of both the export and import of a specified month of a specified year.");
        System.out.println("yearly_total: Provides an overview of all the monthly totals for a particular year.");
        System.out.println("yearly_average: Provides an overview of all the monthly averages for a particular year, for both import and export. Then it gives the yearly average for both import and export.");
        System.out.println("overview: Returns all the unique values that span the data set: years, countries, commodities, transportation modes, and measures.");
        System.out.println("Exit : quit the program");
    }
    public static void Command(String command){
        switch (command){
            case "help" -> {
                System.out.println("help is a command we allow you to know what are all commands and what they do");
            }
            case "monthly_total" -> {
                System.out.println("monthly_total: Returns the sum of both the export and import for a specified month of a specified year. The command take two parameters month (MM) and year (YYYY) if none return nothing.");
                System.out.println("Have the following parameters available to customize their query:");
                System.out.println("- country (default: \"all\")");
                System.out.println("- commodity (default: \"all\")");
                System.out.println("- transport_mode (default: \"all\")");
                System.out.println("- measure (default: \"$\")");
                System.out.println("If you want to modify one parameter don't forget to set others to \"all\" or $ in this order : monthly_total <mm/yyyy> <country> <commodity> <transport_mode> <measure>");

            }
            case "monthly_average" -> {
                System.out.println("monthly_average: Returns the average of both the export and import of a specified month of a specified year. The command take two parameters month (MM) and year (YYYY)");
                System.out.println("Have the following parameters available to customize their query:");
                System.out.println("- country (default: \"all\")");
                System.out.println("- commodity (default: \"all\")");
                System.out.println("- transport_mode (default: \"all\")");
                System.out.println("- measure (default: \"$\")");
                System.out.println("If you want to modify one parameter don't forget to set others to \"all\" or $ in this order : monthly_average <mm/yyyy> <country> <commodity> <transport_mode> <measure>");

            }
            case "yearly_total" -> {
                System.out.println("yearly_total: Provides an overview of all the monthly totals for a particular year. This command returns the total of each month for both import and export and then gives the yearly total for both import and export.");
                System.out.println("Have the following parameters available to customize their query:");
                System.out.println("- country (default: \"all\")");
                System.out.println("- commodity (default: \"all\")");
                System.out.println("- transport_mode (default: \"all\")");
                System.out.println("- measure (default: \"$\")");
                System.out.println("If you want to modify one parameter don't forget to set others to \"all\" or $ in this order : yearly_total <yyyy> <country> <commodity> <transport_mode> <measure>");

            }
            case "yearly_average" -> {
                System.out.println("yearly_average: Provides an overview of all the monthly averages for a particular year, for both import and export. Then it gives the yearly average for both import and export.");
                System.out.println("Have the following parameters available to customize their query:");
                System.out.println("- country (default: \"all\")");
                System.out.println("- commodity (default: \"all\")");
                System.out.println("- transport_mode (default: \"all\")");
                System.out.println("- measure (default: \"$\")");
                System.out.println("If you want to modify one parameter don't forget to set others to \"all\" or $ in this order : yearly_average <yyyy> <country> <commodity> <transport_mode> <measure>");
            }
            case "overview" -> {
                System.out.println("Returns all the unique values that span the data set: years, countries, commodities, transportation modes, and measures.");
            }
        }
    }
}

class Monthly{
    public static void Total(String command){
            String[] words = command.split(" ");
        if (words.length == 2){
            CSVRead csvReader = new CSVRead();
            String[] date = words[1].split("/");
            int month = parseInt(date[0]);
            int year = parseInt(date[1]);
            String [] values = csvReader.monthlyTotal(month, year, "All", "All", "All", "$");
            System.out.println("Monthly total of exports of" + values[0] + " " + values[1] + " : " + values[3]);
            System.out.println("Monthly total of imports of" + values[0] + " " + values[1] + " : " + values[2]);
        }
        if (words.length > 2){
            String[] word = remove.chevron(command); // Element 0
            CSVRead csvReader = new CSVRead();
            //System.out.println(word[0]); // == monthly_total 01/2015
            String[] firstElem = word[0].split(" ");
            String[] date = firstElem[1].split("/");

            int month = parseInt(date[0]);
//            System.out.println("month number is : " + month);
            int year = parseInt(date[1]);
            String country = word[1];
//            System.out.println(country);
            String commodity = word[3];
            String transport_Mode = word[5];
            String measure = word[7];
            String [] values = csvReader.monthlyTotal(month, year, country, commodity, transport_Mode, measure);
            System.out.println("Monthly total of exports of " + values[0] + " " + values[1] + " : " + values[3]);
            System.out.println("Monthly total of imports of " + values[0] + " " + values[1] + " : " + values[2]);

        }
    }

    public static void average(String command){
        String[] words = command.split(" ");
        if (words.length == 2){
            CSVRead csvReader = new CSVRead();
            String[] date = words[1].split("/");
            int month = parseInt(date[0]);
            int year = parseInt(date[1]);
            String [] values = csvReader.monthlyAverage(month, year, "All", "All", "All", "$");
            System.out.println("Monthly average of exports of" + values[0] + " " + values[1] + " : " + values[3]);
            System.out.println("Monthly average of imports of" + values[0] + " " + values[1] + " : " + values[2]);


        }
        if (words.length == 6){
            String[] word = remove.chevron(command); // Element 0
            CSVRead csvReader = new CSVRead();
            String[] firstElem = word[0].split(" ");
            String[] date = firstElem[1].split("/");

            int month = parseInt(date[0]);
            int year = parseInt(date[1]);
            String country = word[1];
            String commodity = word[3];
            String transport_Mode = word[5];
            String measure = word[7];
            String [] values = csvReader.monthlyAverage(month, year, country, commodity, transport_Mode, measure);
            System.out.println("Monthly average of exports of" + values[0] + " " + values[1] + " : " + values[3]);
            System.out.println("Monthly average of imports of" + values[0] + " " + values[1] + " : " + values[2]);
        }
    }
}

class Yearly{
    public static void Total(String command){
        String[] words = command.split(" ");
        if (words.length == 2) {
            CSVRead csvReader = new CSVRead();
            int year = parseInt(words[1]);
            Double [] values = csvReader.yearlyTotal(year, "All", "All", "All", "$", true);
            System.out.println("Total of import of year " + values[0] + " : " + values[1]);
            System.out.println("Total of export of year " + values[0] + " : " + values[2]);
        }
        if (words.length == 6){
            String[] word = remove.chevron(command);
            CSVRead csvReader = new CSVRead();
            String[] firstElem = word[0].split(" ");
            String dateYear = firstElem[1];
            int year = parseInt(dateYear);
            String country = word[1];
            String commodity = word[3];
            String transport_Mode = word[5];
            String measure = word[7];
            Double [] values = csvReader.yearlyTotal(year, country, commodity, transport_Mode, measure, true);
            System.out.println("Total of import of year " + values[0] + " : " + values[1]);
            System.out.println("Total of export of year " + values[0] + " : " + values[2]);
        }
    }
    public static void Average(String command){
        String[] words = command.split(" ");
        if (words.length == 2) {
            CSVRead csvReader = new CSVRead();
            int year = parseInt(words[1]);
            csvReader.yearlyAverage(year, "All", "All", "All", "$");
        }
        if (words.length == 6){
            String[] word = remove.chevron(command);
            CSVRead csvReader = new CSVRead();
            String[] firstElem = word[0].split(" ");
            String dateYear = firstElem[1];
            int year = parseInt(dateYear);
            String country = word[1];
            String commodity = word[3];
            String transport_Mode = word[5];
            String measure = word[7];
            csvReader.yearlyAverage(year, country, commodity, transport_Mode, measure);
        }
    }
}

class Overview{
    public static void Command(){
        CSVRead csvReader = new CSVRead();
        List<TradeData> allObjects = csvReader.toObject();
        // Set all unique columns
        Set<String> uniqueDirection = new HashSet<>();
        Set<String> uniqueYear = new HashSet<>();
        Set<String> uniqueCountry = new HashSet<>();
        Set<String> uniqueCommodity = new HashSet<>();
        Set<String> uniqueTransportMode = new HashSet<>();
        Set<String> uniqueMeasure = new HashSet<>();

        // iterate on all objects
        for (TradeData obj : allObjects) {
            uniqueDirection.add(obj.getDirection());
            uniqueYear.add(Integer.toString(obj.getYear()));
            uniqueCountry.add(obj.getCountry());
            uniqueCommodity.add(obj.getCommodity());
            uniqueTransportMode.add(obj.getTransport_Mode());
            uniqueMeasure.add(obj.getMeasure());
        }
        // Converting to list of strings
        List<String> uniqueDirectionList = new ArrayList<>(uniqueDirection);
        List<String> uniqueYearList = new ArrayList<>(uniqueYear);
        List<String> uniqueCountryList = new ArrayList<>(uniqueCountry);
        List<String> uniqueCommodityList = new ArrayList<>(uniqueCommodity);
        List<String> uniqueTransportModeList = new ArrayList<>(uniqueTransportMode);
        List<String> uniqueMeasureList = new ArrayList<>(uniqueMeasure);

        //Printing
        System.out.println("Unique values for Direction : " + uniqueDirectionList);
        System.out.println("Unique values for Year : " + uniqueYearList);
        System.out.println("Unique values for Country : " + uniqueCountryList);
        System.out.println("Unique values for Commodity : " + uniqueCommodityList);
        System.out.println("Unique values for Transport Mode : " + uniqueTransportModeList);
        System.out.println("Unique values for Measure : " + uniqueMeasureList);

    }
}

public class CleanProject {
    public static void main(String[] args) {
        String command = Input.Command();
        try{
        Which.Command(command);
        }
        catch (Exception e){
            System.out.println("something went wrong, maybe there's no Import or Export for the command you type.");
        }
    }
}
