package simpl.interpreter;

import simpl.parser.Symbol;
import simpl.interpreter.RuntimeError;

public class Env { //这里表示了Symbol x到 Value v的映射  E是parent Environment

    private final Env E;
    private final Symbol x;
    private final Value v; 

    private Env() {
        E = null;
        x = null;
        v = null;
    }

    public static Env empty = new Env() {
        public Value get(Symbol y) {
            return null;
        }

        public Env clone() {
            return this;
        }
    };

    public Env(Env E, Symbol x, Value v) {
        this.E = E;
        this.x = x;
        this.v = v;
    }

    public Value get(Symbol y) throws RuntimeError{
        if (x == y) {
            return v;
        } else {
            if (E == null) {
                throw new RuntimeError(x.toString() + " not defined");
            }
            return E.get(y);
        }
    }

    public Env clone() {
        // TODO
        return null;
    }
}
