package deloppgave3;

public class Tre {
    TreNode rot;
    String string = "";

    public Tre(){
        rot = null;
    }

    private boolean erOperatorPlussMinus(Object c){
        try {
            if (c.equals('+')|| (c.equals('-'))) {
                return true;
            }
            return false;
        }catch (Exception e){
            return false;
        }
    }

    private void inorden(TreNode n) {
        if (n!=null) {
            if(erOperatorPlussMinus(n.element)){
                string += "(";
            }
            inorden(n.venstre);
            string += (n.element);
            inorden(n.høyre);

            if(erOperatorPlussMinus(n.element)){
                string += ")";
            }

        }
    }

    private double regnUt(TreNode n){
        if(n == null){
            return 0;
        }

        if(n.harBarn()){
            return (int) n.element;
        }


        return regnUtBlader(n.element, regnUt(n.venstre), regnUt(n.høyre));
    }

    private double regnUtBlader(Object o, double v, double h){
        if(o.equals('+')){
            return v + h;
        }
        if(o.equals('-')){
            return v - h;
        }
        if(o.equals('*')){
            return v * h;
        }
        return v / h;
    }

    public double regnUttrykk(){
        return regnUt(rot);
    }

    public void printInorden() {
        inorden(rot);
        System.out.println(string);
    }


}
