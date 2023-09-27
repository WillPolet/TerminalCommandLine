public class remove {
    public static String[] chevron(String input) {
        // Use regular expressions to split the input string
        String[] parts = input.split("[<>]+");

        // Remove any leading or trailing whitespace from each part
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }

        // Display the resulting array
//        for (String part : parts) {
//            System.out.println(part);
//        }
        return parts;
    }
}
