public class UniqueWord {

    private String Word;
    private int value;

    public UniqueWord(String word) {
        Word = word;
        this.value = 1;
    }

    public int getValue() {
        return value;
    }

    public String getWord() {
        return Word;
    }

    public void incValue() {
        this.value++;
    }
}
