package myfilesystem;

public class MyLine {
    private StringBuilder text;
    private int symbolsCount;

    public MyLine() {
        text = new StringBuilder();
        symbolsCount = 0;
    }

    public MyLine(String text) {
        this.text = new StringBuilder(text);
        symbolsCount = text.length();
    }

    public void setText(String text) {
        this.text = new StringBuilder(text);
        this.symbolsCount = text.length();
    }

    public void setTextByAppend(String text) {
        this.text.append(text);
        this.symbolsCount = text.length();
    }

    public String getText() {
        return text.toString();
    }

    public int getSymbolsCount() {
        return symbolsCount;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((text == null) ? 0 : text.toString().hashCode());
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
        MyLine other = (MyLine) obj;
        return (text == null && other.text == null) || (text.toString().equals(other.text.toString()));
    }
}
