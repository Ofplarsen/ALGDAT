package deloppgave3;

public class TreNode {
    Object element;
    TreNode venstre;
    TreNode høyre;
    TreNode forelder;

    public TreNode(Object e, TreNode f, TreNode v, TreNode h){
        element = e;
        venstre = v;
        høyre = h;
        forelder = f;
    }

    @Override
    public String toString() {
        return "TreNode{" +
                "element=" + element +
                ", venstre=" + venstre +
                ", høyre=" + høyre +
                ", forelder=" + forelder +
                '}';
    }

    public boolean harBarn(){
        return venstre == null && høyre == null;
    }

}
