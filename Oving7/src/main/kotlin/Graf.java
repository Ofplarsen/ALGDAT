import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Graf {
    int K, N;
    Node []node;
    int antallKanter;
    int antallNoder;

    public void ny_vgraf(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        node = new Node[N];
        antallNoder = N;
        for(int i = 0; i < N; i++){
            node[i] = new Node();
            node[i].setPlacement(i);
        }
        K = Integer.parseInt(st.nextToken());
        antallKanter = K;
        for(int i = 0; i < K; ++i){

            st = new StringTokenizer(br.readLine());
            int fra = Integer.parseInt(st.nextToken());
            int til = Integer.parseInt(st.nextToken());
            int vekt = Integer.parseInt(st.nextToken());
            VKant k1 = new VKant(node[til], node[fra].getKant1(), vekt, node[fra]);
            node[fra].setKant1((VKant) k1);
        }

        for(int i = 0; i < N; ++i){

            VKant kant = node[i].getKant1();
            while (kant != null && kant.getKapasitet() != 0){

                if(!checkForMotsattKant(kant)){
                    kant.getTil().addKant(kant.motsattKant);
                }
                if(kant.getNeste() != null && kant.getNeste().getKapasitet() != 0){
                    kant = (VKant) kant.getNeste();

                }else{
                    break;
                }
            }
        }


    }


    private boolean checkForMotsattKant(VKant kantToCheck){
        if(kantToCheck.getTil().getKant1() == null){
            VKant k2 = new VKant(kantToCheck.getFra(), null,0, kantToCheck.getTil());
            kantToCheck.setMotsattKant(k2);
            k2.setMotsattKant(kantToCheck);
            antallKanter++;
        }

        VKant kantSomMatcher = kantToCheck.getTil().getKant1();
        while (kantSomMatcher != null){
            if(kantSomMatcher.getTil() == kantToCheck.getFra()){
                kantSomMatcher.setMotsattKant(kantToCheck);
                kantToCheck.setMotsattKant(kantSomMatcher);
                return true;
            }
            if(kantSomMatcher.getNeste() == null){
                VKant k2 = new VKant(kantToCheck.getFra(), null, 0, kantToCheck.getTil());
                kantToCheck.setMotsattKant(k2);
                k2.setMotsattKant(kantToCheck);
                antallKanter++;
            }
            kantSomMatcher = (VKant) kantSomMatcher.getNeste();
        }
        return false;
    }

    public void initPrev(Node s){
        for(int i = antallNoder; i -->0;){
            node[i].setD(new Previous());
        }
        ((Previous)s.getD()).dist = 0;
    }

    public int maxFlow(Previous p, Node end){
        int flyt = 1000000000;
        Previous prev = p;
        ArrayList<VKant> kanter = new ArrayList<>();

        while(true){

            for (VKant k = prev.prev.getD().prev.getKant1(); k != null; k = (VKant) k.getNeste()){
                Node fremst = prev.prev;
                Node sjekkeFra = prev.prev.getD().prev;

                if(sjekkeFra.equals(k.getFra()) && fremst.equals(k.getTil())){

                    kanter.add(k);
                }
            }
            prev = prev.prev.getD();
            if(prev.prev.getD().prev == null){
                break;
            }
        }
        Node test = p.prev;
        while (true){
            if(test.getKant1() == null || test.equals(end)){
                break;
            }

            if(test.getKant1().getRestKap() > 0){
                kanter.add(test.getKant1());
                test = test.getKant1().getTil();
            }else{
                break;
            }

        }



        for (VKant k : kanter){
            if(flyt > k.getRestKap()){
                flyt = k.getRestKap();
            }
        }

        for (VKant k : kanter){
            k.increaseFlow(flyt);
        }

        return flyt;
    }



    public Node bfs(Node s, Node t){
        initPrev(s);
        Queue queue = new Queue(antallNoder - 1);
        //Legger startnoden i queue
        queue.addToQueue(s);
        Previous prev = null;
        Node n = null;
        while (!queue.empty()) {
            //Henter ut neste node som skal sjekker
            n = (Node) queue.nextInQueue();
            //Sjekker hver kant i noden som er hentet ut
            for(VKant k = n.getKant1(); k != null; k = (VKant) k.getNeste()){

                prev = (Previous) k.getTil().getD();
                if(prev.dist == Previous.inf && k.getRestKap() > 0){
                    prev.dist = ((Previous)n.getD()).dist + 1;
                    prev.prev = n;

                    queue.addToQueue(k.getTil());


                }

            }
        }
        return n;
    }

}

