package myfilesystem;

import java.util.ArrayList;
import java.util.List;

public class MyFile {
    private List<MyLine> content;
    private String name;
    private int size;

    public MyFile(String name) {
        content = new ArrayList<MyLine>();
        this.name = name;
        this.size = content.size();
    }

    private int calculateSize() {
        int size = content.size();
        for (MyLine l : content) {
            size += l.getSymbolsCount();
        }
        return size;
    }

    private void setSize() {
        this.size = calculateSize();
    }

    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    public void addLine(String text) {
        MyLine newLine = new MyLine(text);
        content.add(newLine);
        setSize();
    }

    public void setTextAtLine(int lineIndex, String text, Boolean overwrite) {
        if (overwrite) {
            content.get(lineIndex - 1).setText(text);
        } else {
            content.get(lineIndex - 1).setTextByAppend(text);
        }
        setSize();
    }

    public void printContent() {
        for (MyLine l : content) {
            System.out.println(l.getText());
        }
    }

    public boolean isLine(int index) {
        return content.size() >= index;
    }

    public int getLineCount() {
        return content.size();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MyFile other = (MyFile) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }
}
