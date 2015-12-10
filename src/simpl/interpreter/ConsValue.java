package simpl.interpreter;

public class ConsValue extends Value {

    public final Value v1, v2;

    public ConsValue(Value v1, Value v2) {
        this.v1 = v1;
        this.v2 = v2;
    }
    
    public int getLength() {
        if (v2 instanceof ConsValue) {
            return ((ConsValue) v2).getLength() + 1;
        } else {
            return 1;
        }
    }

    public String toString() {
        return "list@" + getLength();
    }

    @Override
    public boolean equals(Object other) {
        //TODO
        return false;
    }
}
