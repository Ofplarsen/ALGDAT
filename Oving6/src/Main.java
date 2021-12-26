import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Olav Finne PrÃ¦steng Larsen
 * @author William Gjerberg Tresselt
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException { //Send in file names for BFS and then topological

        System.out.println("~~~BREADTH FIRST SEARCH~~~");
        BufferedReader brBFS = new BufferedReader(new FileReader(args[0]));
        Graph graph1 = new Graph();

        try {
            graph1.getGraphFromFile(brBFS);
        }catch (IOException e){
            e.printStackTrace();
        }

        int startVertex = 5; //Change number to change start vertex for bfs
        graph1.bfs(graph1.vertices[startVertex]);
        graph1.printVertexPrevAndDistance();

        System.out.println("\n\n");

        System.out.println("~~~TOPOLOGICAL~~~");
        BufferedReader brTop = new BufferedReader(new FileReader(args[1]));
        Graph graph2 = new Graph();

        try {
            graph2.getGraphFromFile(brTop);
        }catch (IOException e){
            e.printStackTrace();
        }

        Vertex v = graph2.topologiSort();
        TopologicalSortStructure t = (TopologicalSortStructure) v.d;
        while(true){
            System.out.print(v.placement + " ");
            v = t.next;
            if(v == null){
                break;
            }
            t = (TopologicalSortStructure) v.d;
        }
    }
}