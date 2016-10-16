package com.theironyard;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    static HashMap<String, String> combineMaps(ArrayList<HashMap<String, String>> variables) {
        HashMap<String, String> map = new HashMap<>();
        for (HashMap<String, String> m : variables) {
            map.putAll(m);
        }
        return map;
    }

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

    static void processCommand(Scanner scanner, ArrayList<HashMap<String, String>> variables, String line) throws Exception {
        line = line.trim();
        if (line.isEmpty()) {
            return;
        }
        String cmd = line, params = "";
        if (line.indexOf(" ") > 0) {
            cmd = line.substring(0, line.indexOf(" "));
            params = line.substring(line.indexOf(" ")+1);
        }
        HashMap<String, String> vars = combineMaps(variables);
        if (vars.containsKey(params)) {
            params = vars.get(params);
        }
        switch (cmd) {
            case "PREACH_IT":
                System.out.println(params);
                break;
            case "KEEP_DAT":
                String variable = params.substring(0, params.indexOf(" "));
                String value = params.substring(params.indexOf(" ") + 1);
                variables.get(variables.size()-1).put(variable, value);
                break;
            case "KEEP_DOIN_DAT":
                int iterations = Integer.valueOf(params);
                ArrayList<String> commands = readLinesInScope(scanner);
                for (int i = 0; i < iterations; i++) {
                    variables.add(new HashMap<>());
                    for (String command : commands) {
                        processCommand(scanner, variables, command);
                    }
                    variables.remove(variables.size()-1);
                }
                break;
            case "IF_DAT_BOOTY_KEEP_POPPIN":
                boolean condition = Boolean.valueOf(params);
                ArrayList<String> commands2 = readLinesInScope(scanner);
                if (condition) {
                    variables.add(new HashMap<>());
                    for (String command : commands2) {
                        processCommand(scanner, variables, command);
                    }
                    variables.remove(variables.size()-1);
                }
                break;
            default:
                throw new Exception("Command not found: " + cmd);
        }
    }

    public static void main(String[] args) throws Exception {
        File f = new File("test.iron");
        Scanner scanner = new Scanner(f);
        ArrayList<HashMap<String, String>> variables = new ArrayList<>();
        variables.add(new HashMap<>());
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            processCommand(scanner, variables, line);
        }
    }
}
