package resources.desenvolvimento;

import java.util.ArrayList;

public class ApiFileEntry {
    private String categoryName;
    private int categoryValue;
    public static ArrayList DirectorialFilesFound = new ArrayList<>();

    public ApiFileEntry(String name, int value) {
        this.categoryName = name;
        this.categoryValue = value;
    }

    public String getCategoryName() { return categoryName; }
    public int getCategoryValue() { return categoryValue; }
}