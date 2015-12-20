package simpl.parser.ast;

import simpl.interpreter.Env;
import simpl.interpreter.RefValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.RefType;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.typing.TypeVar;

public class Assign extends BinaryExpr {

    public Assign(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return l + " := " + r;
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult l_type = l.typecheck(E);
        Substitution sub = l_type.s;
        TypeVar a = new TypeVar(true);
        sub = l_type.t.unify(new RefType(a)).compose(sub);
        
        TypeResult r_type = r.typecheck(sub.compose(E));
        sub = r_type.s.compose(sub);
        
        sub = r_type.t.unify(sub.apply(a)).compose(sub);
        return TypeResult.of(sub,Type.UNIT);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        RefValue l_v = (RefValue)l.eval(s);
        Value r_v = r.eval(s);
        s.M.put(l_v.p, r_v);
        return Value.UNIT;
    }
}
