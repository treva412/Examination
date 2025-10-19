package com.mycompany.accomodationarea;

import java.util.Scanner;

abstract class AccommodationArea {
    protected String areaName;
    protected int occupants;
    protected boolean[] lights;
    
    public AccommodationArea(String areaName, int maxLights) {
        this.areaName = areaName;
        this.occupants = 0;
        this.lights = new boolean[maxLights];
        // Initialize all lights to OFF
        for (int i = 0; i < lights.length; i++) {
            lights[i] = false;
        }
    }
    
    public void addOccupants(int n) {
        if (n > 0) {
            occupants += n;
            System.out.println("Added " + n + " occupants. Total: " + occupants);
        }
    }
    
    public void removeOccupants(int n) {
        if (n > 0) {
            occupants = Math.max(0, occupants - n);
            System.out.println("Removed " + n + " occupants. Total: " + occupants);
        }
    }
    
    public void switchLightOn(int lightNumber) {
        if (isValidLightNumber(lightNumber)) {
            lights[lightNumber - 1] = true;
            System.out.println("Light " + lightNumber + " switched ON");
        } else {
            System.out.println("Invalid light number! Must be between 1 and " + lights.length);
        }
    }
    
    public void switchLightOff(int lightNumber) {
        if (isValidLightNumber(lightNumber)) {
            lights[lightNumber - 1] = false;
            System.out.println("Light " + lightNumber + " switched OFF");
        } else {
            System.out.println("Invalid light number! Must be between 1 and " + lights.length);
        }
    }
    
    private boolean isValidLightNumber(int lightNumber) {
        return lightNumber >= 1 && lightNumber <= lights.length;
    }
    
    public void reportStatus() {
        System.out.println("\n=== " + areaName + " Status Report ===");
        System.out.println("Occupants: " + occupants);
        System.out.print("Lights: ");
        for (int i = 0; i < lights.length; i++) {
            System.out.print("Light " + (i + 1) + ": " + (lights[i] ? "ON" : "OFF"));
            if (i < lights.length - 1) System.out.print(" | ");
        }
        System.out.println("\n" + "=".repeat(40));
    }
    
    public String getAreaName() {
        return areaName;
    }
}

// Gym Area class
class GymArea extends AccommodationArea {
    private int equipmentCount;
    
    public GymArea() {
        super("Gym Area", 3); // Gym has 3 lights
        this.equipmentCount = 15; // Default equipment count
    }
    
    public void displayGymInfo() {
        System.out.println("Gym Equipment Available: " + equipmentCount + " pieces");
    }
}

// Swimming Area class
class SwimmingArea extends AccommodationArea {
    private double waterTemperature;
    
    public SwimmingArea() {
        super("Swimming Pool Area", 3); // Swimming area has 3 lights
        this.waterTemperature = 28.5; // Default temperature in Celsius
    }
    
    public void displayPoolInfo() {
        System.out.println("Water Temperature: " + waterTemperature + "°C");
    }
}

// Main application class
public class AccomodationArea {
    private GymArea gym;
    private SwimmingArea swimmingPool;
    private AccommodationArea activeArea;
    private Scanner scanner;
    
    public AccomodationArea() {
        this.gym = new GymArea();
        this.swimmingPool = new SwimmingArea();
        this.activeArea = gym; // Default active area
        this.scanner = new Scanner(System.in);
    }
    
    public void run() {
        System.out.println("==========================================");
        System.out.println("  Speke Apartments Accommodation Manager");
        System.out.println("==========================================");
        
        String command;
        do {
            displayMenu();
            System.out.print("Enter command: ");
            command = scanner.nextLine().trim().toUpperCase();
            
            switch (command) {
                case "S":
                    selectActiveArea();
                    break;
                case "W":
                    addOccupants();
                    break;
                case "X":
                    removeOccupants();
                    break;
                case "Y":
                    switchLightOn();
                    break;
                case "Z":
                    switchLightOff();
                    break;
                case "R":
                    reportStatus();
                    break;
                case "Q":
                    System.out.println("Thank you for using Speke Apartments Manager. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid command! Please try again.");
            }
            System.out.println();
            
        } while (!command.equals("Q"));
        
        scanner.close();
    }
    
    private void displayMenu() {
        System.out.println("\nMain Menu - Active Area: " + activeArea.getAreaName());
        System.out.println("S: Select active area (G = Gym, P = Swimming)");
        System.out.println("W: Add n occupants to active area");
        System.out.println("X: Remove n occupants from active area");
        System.out.println("Y: Switch ON a light (1-3)");
        System.out.println("Z: Switch OFF a light (1-3)");
        System.out.println("R: Report status");
        System.out.println("Q: Quit the program");
    }
    
    private void selectActiveArea() {
        System.out.print("Select area (G = Gym, P = Swimming): ");
        String areaChoice = scanner.nextLine().trim().toUpperCase();
        
        switch (areaChoice) {
            case "G":
                activeArea = gym;
                System.out.println("Active area set to: Gym");
                gym.displayGymInfo();
                break;
            case "P":
                activeArea = swimmingPool;
                System.out.println("Active area set to: Swimming Pool");
                swimmingPool.displayPoolInfo();
                break;
            default:
                System.out.println("Invalid area selection! Please enter G for Gym or P for Swimming Pool.");
        }
    }
    
    private void addOccupants() {
        int n = getValidInteger("Enter number of occupants to add: ");
        activeArea.addOccupants(n);
    }
    
    private void removeOccupants() {
        int n = getValidInteger("Enter number of occupants to remove: ");
        activeArea.removeOccupants(n);
    }
    
    private void switchLightOn() {
        int lightNumber = getValidLightNumber("Enter light number to switch ON (1-3): ");
        activeArea.switchLightOn(lightNumber);
    }
    
    private void switchLightOff() {
        int lightNumber = getValidLightNumber("Enter light number to switch OFF (1-3): ");
        activeArea.switchLightOff(lightNumber);
    }
    
    private void reportStatus() {
        activeArea.reportStatus();
        
        // Display additional area-specific information
        if (activeArea instanceof GymArea) {
            ((GymArea) activeArea).displayGymInfo();
        } else if (activeArea instanceof SwimmingArea) {
            ((SwimmingArea) activeArea).displayPoolInfo();
        }
    }
    
    // Utility method to get valid integer input
    private int getValidInteger(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                int value = Integer.parseInt(input);
                if (value >= 0) {
                    return value;
                } else {
                    System.out.println("Please enter a non-negative integer.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid integer.");
            }
        }
    }
    
    // Utility method to get valid light number (1-3)
    private int getValidLightNumber(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                int lightNumber = Integer.parseInt(input);
                if (lightNumber >= 1 && lightNumber <= 3) {
                    return lightNumber;
                } else {
                    System.out.println("Light number must be between 1 and 3. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number between 1 and 3.");
            }
        }
    }
    
    // Main method to start the application
    public static void main(String[] args) {
        AccomodationArea manager = new AccomodationArea();
        manager.run();
                
    }
}