package pl.programisci.task_crud.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MenuService {
    private final static int NUMBER_OF_FILE = 1;

    public String[] firstMenuItmes;


    public MenuService() {
        firstMenuItmes = new String[]{"Text file", "Excel file"};
    }

    public Map<String, List<String>> runMenu() {
        showFormatMenu();
        String format = formatMenuUserImput();
        List<String> fileName = nameFiles();
        Map<String, List<String>> menu = new HashMap<String, List<String>>();
        menu.put(format, fileName);
        return menu;
    }

    private void showFormatMenu() {
        int i = 1;
        System.out.println("Wybierz 1 lub 2 i wciśnij enter");
        for (String menu : firstMenuItmes) {
            System.out.println(i + " " + menu);
            i++;
        }
    }

    private String formatMenuUserImput() {
        Scanner userInput = new Scanner(System.in);
        String next = userInput.next();
        if (next.equals("1")) {
            System.out.println("File writer \n");
            return "File";
        } else {
            return "Excel";
        }
    }

    private List<String> nameFiles() {
        List<String> fileNames = new ArrayList<>();
        Scanner userInput = new Scanner(System.in);
        for (int i = 0; i <= NUMBER_OF_FILE; i++) {
            System.out.println("Wprowadź nazwę pliku:\n");
            String textFile = userInput.nextLine();
            fileNames.add(textFile);
        }
        return fileNames;
    }
}
