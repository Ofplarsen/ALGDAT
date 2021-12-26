package deloppgave1;

public class Node {
    double element;
    Node neste;
    public Node(double e, Node n){
        element = e;
        neste = n;
    }

    public double finnElement(){
        return element;
    }

    public Node finnNeste(){
        return neste;
    }

    @Override
    public String toString() {
        return "deloppgave1.Node{" +
                "element=" + element +
                '}';
    }
}
