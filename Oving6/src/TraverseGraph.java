import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;

class Vertex{ //Node
    Edge edge1;
    Edge edge2;
    int placement;
    Object d; //Other vertex data

    public String toString(){
        return d.toString();
    }
}

class Edge{ //Kant
    Edge next;
    Vertex to;
    public Edge(Vertex to, Edge next){
        this.next = next;
        this.to = to;
    }
}

class Graph{ //Graf
    int amountOfVertices;
    int amountOfEdges;
    Vertex[] vertices;

    public void printVertexPrevAndDistance(){
        System.out.println("Vertex   Previous   Distance");
        for(int i = 0; i < vertices.length; i++){
            Previous p = (Previous)vertices[i].d;
            if(p.prev == null){
                System.out.println( i + "        " + "none" + "        " + 0 );
            }
            else{
                System.out.println( i + "        " + p.prev.placement + "           " + p.dist );
            }
        }
    }

    public void getGraphFromFile(BufferedReader reader) throws IOException{
        StringTokenizer s = new StringTokenizer(reader.readLine());
        amountOfVertices = Integer.parseInt(s.nextToken());
        vertices = new Vertex[amountOfVertices];
        for(int i = 0; i < amountOfVertices; ++i) {
            vertices[i] = new Vertex();
            vertices[i].placement = i;
        }
        amountOfEdges = Integer.parseInt(s.nextToken());
        for(int i = 0; i<amountOfEdges; ++i){
            s = new StringTokenizer(reader.readLine());
            int from = Integer.parseInt(s.nextToken());
            int to = Integer.parseInt(s.nextToken());
            Edge e1 = new Edge(vertices[to], vertices[from].edge1);
            Edge e2 = new Edge( vertices[from], vertices[to].edge2);
            vertices[from].edge1 = e1;
            vertices[to].edge2 = e2;
        }
    }
    public void initPrev(Vertex s){
        for(int i = amountOfVertices; i -->0;){
            vertices[i].d = new Previous();
        }
        ((Previous)s.d).dist = 0;
    }

    public void bfs(Vertex s){
        initPrev(s);
        Queue queue = new Queue(amountOfVertices - 1);
        queue.addToQueue(s);

        while (!queue.empty()) {
            Vertex v = (Vertex) queue.nextInQueue();
            for(Edge e = v.edge1; e != null; e = e.next){
                Previous prev = (Previous) e.to.d;
                if(prev.dist == prev.inf){
                    prev.dist = ((Previous)v.d).dist + 1;
                    prev.prev = v;
                    queue.addToQueue(e.to);
                }
            }
        }
    }

    //Topological
    Vertex depthFirstTopo(Vertex v1, Vertex v2){
        TopologicalSortStructure nd = (TopologicalSortStructure) v1.d;
        if(nd.found) return v2;
        nd.found = true;
        for(Edge e = v1.edge1; e!= null; e=e.next){
            v2 = depthFirstTopo(e.to, v2);
        }
        nd.next = v2;
        return v1;
    }

    Vertex topologiSort(){
        Vertex v = null;
        for(int i = amountOfVertices; i-- > 0;){
            vertices[i].d = new TopologicalSortStructure();
        }
        for(int i = amountOfVertices; i-->0;){
            v = depthFirstTopo(vertices[i], v);
        }
        return v;
    }
}

class TopologicalSortStructure{
    boolean found;
    Vertex next;
}

class Queue{
    private Object[] tab;
    private int start = 0;
    private int end = 0;
    private int amount = 0;

    public Queue(int str){
        tab = new Object[str];
    }
    public boolean empty(){
        return amount == 0;
    }

    public boolean full(){
        return amount == tab.length;
    }

    public void addToQueue(Object e){
        if(full())return;
        tab[end] = e;
        end = (end+1) % tab.length;
        ++amount;
    }

    public Object nextInQueue(){
        if(!empty()){
            Object e = tab[start];
            start = (start +1) % tab.length;
            --amount;
            return e;
        }
        else return null;
    }
}

class Previous{
    int dist;
    Vertex prev;
    static int inf = 1000000000;

    public  int find_dist(){return  dist;}

    public  Vertex find_prev(){return prev;}

    public Previous() {
        dist = inf;
    }
}