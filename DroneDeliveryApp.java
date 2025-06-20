package sem3;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DroneDeliveryApp {

    static class Town {
        String name;
        int supplyNeeded;
        int x, y;

        Town(String name, int supplyNeeded, int x, int y) {
            this.name = name;
            this.supplyNeeded = supplyNeeded;
            this.x = x;
            this.y = y;
        }
    }

    static class Drone {
        int maxRange;
        int maxCapacity;

        Drone(int maxRange, int maxCapacity) {
            this.maxRange = maxRange;
            this.maxCapacity = maxCapacity;
        }
    }

    static List<Town> towns = new ArrayList<>();
    static double[][] distanceMatrix;
    static boolean[] visited;
    static double minDistance = Double.MAX_VALUE;
    static List<Integer> bestRoute = new ArrayList<>();
    static Drone drone;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the input file name (e.g., input1.txt): ");
        String fileName = input.nextLine();
        input.close();

        try {
            Scanner scanner = new Scanner(new File(fileName));
            System.out.println("\n=== 🚁 Reading input from " + fileName + " ===");

            int range = scanner.nextInt();
            int capacity = scanner.nextInt();
            drone = new Drone(range, capacity);

            towns.add(new Town("Base", 0, 0, 0));

            while (scanner.hasNext()) {
                String name = scanner.next();
                if (name.startsWith("#")) { scanner.nextLine(); continue; }
                int supply = scanner.nextInt();
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                towns.add(new Town(name, supply, x, y));
            }

            scanner.close();

            int n = towns.size();
            distanceMatrix = new double[n][n];
            visited = new boolean[n];
            computeDistances();

            visited[0] = true;
            List<Integer> currentPath = new ArrayList<>();
            currentPath.add(0);
            tsp(0, currentPath, 0, 0, drone);

            System.out.println("\n\n=== 📋 Generating Mission Report ===\n");
            printBestRoute();
        } catch (FileNotFoundException e) {
            System.out.println("❌ Error: File not found!");
        }
    }

    static void computeDistances() {
        int n = towns.size();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                distanceMatrix[i][j] = euclidean(towns.get(i), towns.get(j));
            }
        }
    }

    static double euclidean(Town a, Town b) {
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }

    static void tsp(int current, List<Integer> path, double distanceSoFar, int loadSoFar, Drone drone) {
        if (path.size() == towns.size()) {
            double totalDistance = distanceSoFar + distanceMatrix[current][0];
            if (totalDistance <= drone.maxRange && totalDistance < minDistance) {
                minDistance = totalDistance;
                bestRoute = new ArrayList<>(path);
            }
            return;
        }

        for (int i = 1; i < towns.size(); i++) {
            if (!visited[i]) {
                int newLoad = loadSoFar + towns.get(i).supplyNeeded;
                double newDistance = distanceSoFar + distanceMatrix[current][i];

                if (newLoad <= drone.maxCapacity && newDistance <= drone.maxRange) {
                    visited[i] = true;
                    path.add(i);
                    tsp(i, path, newDistance, newLoad, drone);
                    visited[i] = false;
                    path.remove(path.size() - 1);
                }
            }
        }
    }

    static void printBestRoute() {
        if (bestRoute.isEmpty()) {
            System.out.println("❌ No valid route found. Drone range or capacity exceeded.");
            return;
        }

        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy");
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        int totalSupplies = 0;
        for (int i = 1; i < bestRoute.size(); i++) {
            totalSupplies += towns.get(bestRoute.get(i)).supplyNeeded;
        }

        System.out.println("====================================================");
        System.out.println("             🚁 AIDRO-DRONE MISSION REPORT");
        System.out.println("====================================================");
        System.out.println("📦 Mission Code     : MISSION" + now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        System.out.println("📅 Date             : " + dateFmt.format(now));
        System.out.println("🕒 Time             : " + timeFmt.format(now));

        System.out.println("\n------------------ DELIVERY ROUTE ------------------");
        System.out.println("START → Base");
        for (int i = 1; i < bestRoute.size(); i++) {
            System.out.println("       → " + towns.get(bestRoute.get(i)).name);
        }
        System.out.println("       → Base (Return)");
        System.out.println("END");

        System.out.println("\n------------------- TOWNS COVERED ------------------");
        for (int i = 1; i < bestRoute.size(); i++) {
            Town t = towns.get(bestRoute.get(i));
            System.out.printf("🏘️ %-10s | Supplies Sent: %d units\n", t.name, t.supplyNeeded);
        }

        System.out.println("\n-------------------- DRONE INFO --------------------");
        System.out.printf("🔋 Battery Range     : %.2f units\n", (double) drone.maxRange);
        System.out.printf("🛣️ Total Distance    : %.2f units\n", minDistance);
        System.out.printf("📦 Load Capacity     : %d units\n", drone.maxCapacity);
        System.out.printf("🎯 Load Used         : %d units\n", totalSupplies);

        System.out.println("\n------------------- MISSION STATUS -----------------");
        System.out.println("✅ SUCCESS – All towns reached within one trip.");

        System.out.println("\n====================================================");
        System.out.println("     ✅ Emergency Aid Delivered Successfully!");
        System.out.println("====================================================");
    }
}
