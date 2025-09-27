package org.example.smartoffice;

import org.example.smartoffice.manager.OfficeManager;
import org.example.smartoffice.observer.ACController;
import org.example.smartoffice.observer.LightController;
import org.example.smartoffice.commands.*;
import org.example.smartoffice.util.ConsoleColors;
import org.example.smartoffice.util.LoggerUtil;
import org.example.smartoffice.util.ValidationException;
import java.util.*;
import java.util.logging.Logger;

public class SmartOfficeApp {
    private static volatile boolean running = true;
    private static final Logger logger = LoggerUtil.getLogger();

    public static void main(String[] args) {
        OfficeManager manager = OfficeManager.getInstance();
        manager.initRooms(3);

        manager.addObserverToAllRooms(new LightController());
        manager.addObserverToAllRooms(new ACController());

        Map<String, Command> commands = new HashMap<>();
        commands.put("book", new BookRoomCommand(manager));
        commands.put("b", commands.get("book")); // shortcut
        commands.put("cancel", new CancelBookingCommand(manager));
        commands.put("c", commands.get("cancel"));
        commands.put("addOccupant", new AddOccupantCommand(manager));
        commands.put("removeOccupant", new RemoveOccupantCommand(manager));
        commands.put("status", new StatusCommand(manager));
        commands.put("s", commands.get("status"));
        commands.put("config", new ConfigCommand(manager));
        commands.put("exit", new ExitCommand(manager, () -> running = false));
        commands.put("quit", commands.get("exit"));
        commands.put("export", new ExportCommand(manager));


        Scanner sc = new Scanner(System.in);

        System.out.println(ConsoleColors.CYAN + ConsoleColors.BOLD +
                "\n WELCOME TO SMART OFFICE BOOKING APPLICATION!!!\n" + ConsoleColors.RESET);
        System.out.println("You can here explore the features:\n");
        System.out.println("1.\tBook a room\r\n" + //
                        "2.\tCancel a booking\r\n" + //
                        "3.\tAdd people into a room\r\n" + //
                        "4.\tRemove people from a room\r\n" + //
                        "5.\tShow status\r\n" + //
                        "6.\tInitialize rooms\r\n" + //
                        "7.\tChange capacity\r\n" + //
                        "8.\tAuto release timer\r\n" + //
                        "9.\tOccupancy threshold\r\n" + //
                        "10..\tExit the system\r\n" + //
                        "");
        System.out.println("Type 'help' to see available commands.\n");

        while (running) {
            System.out.print(ConsoleColors.BLUE + "> " + ConsoleColors.RESET);
            String line = sc.nextLine();
            if (line == null) break;
            line = line.trim();
            if (line.isEmpty()) continue;

            if ("help".equalsIgnoreCase(line)) {
                printHelp();
                continue;
            }

            String[] parts = line.split("\\s+");
            String cmd = parts[0];
            Command c = commands.get(cmd);
            if (c == null) {
                System.out.println(ConsoleColors.RED + " Unknown command: " + cmd + ConsoleColors.RESET);
                System.out.println("Type 'help' for a list of commands.");
                continue;
            }

            try {
                c.execute(parts);
            } catch (ValidationException ve) {
                System.out.println(ConsoleColors.RED + " Oops! " + ve.getMessage() + ConsoleColors.RESET);
                logger.warning("Validation error: " + ve.getMessage());
            } catch (Exception ex) {
                System.out.println(ConsoleColors.RED + " Error: " + ex.getMessage() + ConsoleColors.RESET);
                logger.severe("Command error: " + ex.getMessage());
            }
        }

        sc.close();
        System.out.println(ConsoleColors.GREEN + " \nThank you for using Smart Office. Have a productive day!\n" + ConsoleColors.RESET);
        logger.info("Application terminated.");
    }

    private static void printHelp() {
        System.out.println(ConsoleColors.BOLD + "\nAvailable Commands:" + ConsoleColors.RESET);
        System.out.println("  book <roomId> <HH:mm> <minutes>   | b                   → Book a room");
        System.out.println("  cancel <bookingId>                | c                   → Cancel a booking");
        System.out.println("  addOccupant <roomId> <count>                            → Add people into a room");
        System.out.println("  removeOccupant <roomId> <count>                         → Remove people from a room");
        System.out.println("  status all | status <id> | status booking <uuid> | s    → Show status");
        System.out.println("  config roomCount <n>                                    → Initialize rooms");
        System.out.println("  config roomCapacity <id> <capacity>                     → Change capacity");
        System.out.println("  config autoReleaseSeconds <s>                           → Auto release timer");
        System.out.println("  config occupancyThreshold <n>                           → Occupancy threshold");
        System.out.println("  exit | quit                                             → Exit the system\n");
    }
}
