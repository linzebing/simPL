package simpl.parser.ast;

import simpl.interpreter.RecValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.typing.TypeVar;

public class Name extends Expr {

    public Symbol x;

    public Name(Symbol x) {
        this.x = x;
    }

    public String toString() {
        return "" + x;
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        Type tmp = E.get(x);
        if(tmp == null)
        {       
            return TypeResult.of(new TypeVar(true));
        }
        else
        {
            return TypeResult.of(tmp);
        }
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        return s.E.get(x);
    }
}
