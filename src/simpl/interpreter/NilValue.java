package simpl.interpreter;

public class NilValue extends Value { //原来不是public

    public NilValue() { //原来是protected
    }

    public String toString() {
        return "nil";
    }

    @Override
    public boolean equals(Object other) {
        
        if (other instanceof NilValue) {
            return true;
        } else {
            return false;
        }
    }
}
