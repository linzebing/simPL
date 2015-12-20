package simpl.parser.ast;

import simpl.interpreter.BoolValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class OrElse extends BinaryExpr {

    public OrElse(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " orelse " + r + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult l_type = l.typecheck(E);
        Substitution sub = l_type.s;
        sub = sub.apply(l_type.t).unify(Type.BOOL).compose(sub);
        
        TypeResult r_type = r.typecheck(sub.compose(E));
        sub = r_type.s.compose(sub);
        sub = sub.apply(r_type.t).unify(Type.BOOL).compose(sub);
        return TypeResult.of(sub,Type.BOOL);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        Value l_v = l.eval(s);
        Value r_v = r.eval(s);
        return new BoolValue(((BoolValue) l_v).b || ((BoolValue) r_v).b);
    }
}
