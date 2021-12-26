public class Queue {
    private Object[] tab;
    private int start = 0;
    private int end = 0;
    private int amount = 0;

    public Queue(int str) {
        tab = new Object[str];
    }

    public boolean empty() {
        return amount == 0;
    }

    public boolean full() {
        return amount == tab.length;
    }

    public void addToQueue(Object e) {
        if (full()) return;
        tab[end] = e;
        end = (end + 1) % tab.length;
        ++amount;
    }

    public Object nextInQueue() {
        if (!empty()) {
            Object e = tab[start];
            start = (start + 1) % tab.length;
            --amount;
            return e;
        } else return null;
    }
}
