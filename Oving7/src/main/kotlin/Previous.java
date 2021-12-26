public class Previous {
    int dist;
    Node prev;
    static int inf = 1000000000;

    public int find_dist() {
        return dist;
    }

    public Node find_prev() {
        return prev;
    }

    public Previous() {
        dist = inf;
    }
}
