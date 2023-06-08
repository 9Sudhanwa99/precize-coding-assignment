import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;

class SATResult {
    private String name;
    private String address;
    private String city;
    private String country;
    private String pincode;
    public int satScore;
    public boolean passed;

    public SATResult(String name, String address, String city, String country, String pincode, int satScore) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.country = country;
        this.pincode = pincode;
        this.satScore = satScore;
        this.passed = calculatePassed();
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getPincode() {
        return pincode;
    }

    public int getSatScore() {
        return satScore;
    }

    public boolean getPassed() {
        return passed;
    }

    public void setSatScore(int satScore) {
        this.satScore = satScore;
        this.passed = calculatePassed();
    }

    private boolean calculatePassed() {
        return satScore > 30;
    }

    @Override
    public String toString() {
        return "SATResult{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", pincode='" + pincode + '\'' +
                ", satScore=" + satScore +
                ", passed=" + passed +
                '}';
    }
}


class SATResultsApp {
    private List<SATResult> satResults;

    public SATResultsApp() {
        satResults = new ArrayList<>();
    }

    public void insertData(String FullName, String address, String city, String country, String pincode, int satScore) {
        for (SATResult result : satResults) {
            if (result.getName().equals(FullName)) {
                System.out.println("Error: Name already exists.");
                return;
            }
        }


        SATResult satResult = new SATResult(FullName, address, city, country, pincode, satScore);
        satResults.add(satResult);
    }




public void viewAllData() {
    JSONArray jsonArray = new JSONArray();
    for (SATResult satResult : satResults) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Name", satResult.getName());
        jsonObject.put("Address", satResult.getAddress());
        jsonObject.put("City", satResult.getCity());
        jsonObject.put("Country", satResult.getCountry());
        jsonObject.put("Pincode", satResult.getPincode());
        jsonObject.put("SAT Score", satResult.getSatScore());
        jsonObject.put("Passed", satResult.getPassed());
        jsonArray.put(jsonObject);
    }
    String json = jsonArray.toString(4); // Indentation of 4 spaces
    System.out.println(json);
}


public int getRank(String name) {
    SATResult targetResult = null;
    for (SATResult satResult : satResults) {
        if (satResult.getName().equals(name)) {
            targetResult = satResult;
            break;
        }
    }

    if (targetResult == null) {
        return -1; // Name not found
    }

    int targetScore = targetResult.getSatScore();

    Collections.sort(satResults, Comparator.comparingInt(SATResult::getSatScore).reversed());

    int rank = 1;
    for (SATResult satResult : satResults) {
        if (satResult.getSatScore() > targetScore) {
            rank++;
        } else if (satResult.getSatScore() == targetScore && satResult.getName().equals(name)) {
            break;
        }
    }

    // Reset the original order of satResults
    Collections.sort(satResults, Comparator.comparingInt(SATResult::getSatScore));

    return rank;
}

    public void updateScore(String name, int satScore) {
        for (SATResult satResult : satResults) {
            if (satResult.getName().equals(name)) {
                satResult.satScore = satScore;
                satResult.passed = satResult.getSatScore() > 30;
                System.out.println("Score updated for " + name);
                return;
            }
        }
        System.out.println("Name not found");
    }

    public void deleteRecord(String name) {
        for (Iterator<SATResult> iterator = satResults.iterator(); iterator.hasNext();) {
            SATResult satResult = iterator.next();
            if (satResult.getName().equals(name)) {
                iterator.remove();
                System.out.println("Record deleted for " + name);
                return;
            }
        }
        System.out.println("Name not found");
    }
}

public class Main {
    public static void main(String[] args) {
        SATResultsApp app = new SATResultsApp();
        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            System.out.println("Menu:");
            System.out.println("1. Insert data");
            System.out.println("2. View all data");
            System.out.println("3. Get rank");
            System.out.println("4. Update score");
            System.out.println("5. Delete one record");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    scanner.nextLine();
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Address: ");
                    String address = scanner.nextLine();
                    System.out.print("City: ");
                    String city = scanner.nextLine();
                    System.out.print("Country: ");
                    String country = scanner.nextLine();
                    System.out.print("Pincode: ");
                    String pincode = scanner.next();
                    System.out.print("SAT score: ");
                    int satScore = scanner.nextInt();
                    app.insertData(name, address, city, country, pincode, satScore);
                    break;
                case 2:
                    app.viewAllData();
                    break;
                case 3:
                    System.out.print("Enter Name: ");
                    String rankName = scanner.next();
                    int rank = app.getRank(rankName);
                    if (rank != -1) {
                        System.out.println("Rank for " + rankName + ": " + rank);
                    } else {
                        System.out.println("Name not found");
                    }
                    break;
                case 4:
                    System.out.print("Enter Name: ");
                    String updateName = scanner.next();
                    System.out.print("Enter new SAT score: ");
                    int updateScore = scanner.nextInt();
                    app.updateScore(updateName, updateScore);
                    break;
                case 5:
                    System.out.print("Enter Name: ");
                    String deleteName = scanner.next();
                    app.deleteRecord(deleteName);
                    break;
                case 0:
                    System.out.println("Exiting the application...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        } while (option != 0);

        scanner.close();
    }
}
