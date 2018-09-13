package myfilesystem;

import java.util.ArrayList;
import java.util.List;

public class MyFile {

    private List<MyLine> content;
    private String name;
    private int size;
    private boolean deleted;

    public MyFile(String name) {
        content = new ArrayList<MyLine>();
        this.name = name;
        this.size = content.size();
        deleted = false;
    }

    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void delete() {
        deleted = true;
    }

    public void deleteLines(int start, int end) throws InvalidArgumentException {
        if (isLine(start) && isLine(end)) {
            if (isLine(end)) {
                for (int i = start - 1; i < end - 1; i++) {
                    content.get(i).setText("");
                }
            } else {
                for (int i = start - 1; i < end - 1; i++) {
                    content.remove(i);
                }
            }
        } else {
            throw new InvalidArgumentException("Invalid line index to be deleted");
        }
    }

    private void addLine(String text) {
        MyLine newLine = new MyLine(text);
        content.add(newLine);
        setSize();
    }

    private boolean isLine(int index) {
        return content.size() >= index;
    }

    public void setTextAtLine(int lineIndex, String text, Boolean overwrite)
            throws InvalidArgumentException {
        if (lineIndex < 1) {
            throw new InvalidArgumentException("Invalid line index");
        } else {
            if (!isLine(lineIndex)) {
                for (int i = content.size() - 1; i < lineIndex - 1; i++) {
                    addLine("");
                }
            }
            if (overwrite) {
                content.get(lineIndex - 1).setText(text);
            } else {
                content.get(lineIndex - 1).setTextByAppend(text);
            }
            setSize();
        }
    }

    private String contentToString() {
        StringBuilder temp = new StringBuilder();
        for (MyLine l : content) {
            temp.append(l.toString() + " ");
        }
        if (temp.length() > 0) {
            temp.deleteCharAt(temp.length() - 1);
        }
        return temp.toString();
    }

    public void printContent() {
        for (MyLine l : content) {
            System.out.println(l.getText());
        }
    }

    public void printLineCount() {
        System.out.println(name + " " + (content.size() - 1) + " lines");
    }

    public void printWordsCount() {
        System.out.println(name + " - " + WordCounter.countText(contentToString()) + " words");
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
