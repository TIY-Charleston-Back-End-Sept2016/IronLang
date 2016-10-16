package com.theironyard;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    static ArrayList<String> readLinesInScope(Scanner scanner) {
        ArrayList<String> commands = new ArrayList<>();
        while (scanner.hasNext()) {
            String line2 = scanner.nextLine();
            if (line2.equals("K_STOP")) {
                break;
            }
            commands.add(line2);
        }
        return commands;
    }

    static void processCommand(Scanner scanner, HashMap<String, String> variables, String line) {
        line = line.trim();
        String cmd = line.substring(0, line.indexOf(" "));
        String params = line.substring(line.indexOf(" ")+1);
        if (variables.containsKey(params)) {
            params = variables.get(params);
        }
        switch (cmd) {
            case "PREACH_IT":
                System.out.println(params);
                break;
            case "KEEP_DAT":
                String variable = params.substring(0, params.indexOf(" "));
                String value = params.substring(params.indexOf(" ") + 1);
                variables.put(variable, value);
                break;
            case "KEEP_DOIN_DAT":
                int iterations = Integer.valueOf(params);
                ArrayList<String> commands = readLinesInScope(scanner);
                for (int i = 0; i < iterations; i++) {
                    for (String command : commands) {
                        processCommand(scanner, variables, command);
                    }
                }
                break;
            case "IF_DAT_BOOTY_KEEP_POPPIN":
                boolean condition = Boolean.valueOf(params);
                ArrayList<String> commands2 = readLinesInScope(scanner);
                if (condition) {
                    for (String command : commands2) {
                        processCommand(scanner, variables, command);
                    }
                }
                break;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        File f = new File("test.iron");
        Scanner scanner = new Scanner(f);
        HashMap<String, String> variables = new HashMap<>();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            processCommand(scanner, variables, line);
        }
    }
}
