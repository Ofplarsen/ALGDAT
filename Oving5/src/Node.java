public class Node {
    String element;
    Node neste;
    int key;
    public Node(String e, Node n, int key){
        element = e;
        neste = n;
        this.key = key;
    }

    public String finnElement(){
        return element;
    }

    public Node finnNeste(){
        return neste;
    }

    public String getKeyAndElement() {
        return element + ", " + key;
    }

    @Override
    public String toString() {
        return "Node{" +
                "element='" + element + '\'' +
                ", neste=" + neste +
                ", key=" + key +
                '}';
    }
}
