package simpl.interpreter;

public class NilValue extends Value { 

    protected NilValue() {
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
