package simpl.parser.ast;

import simpl.interpreter.BoolValue;
import simpl.interpreter.ConsValue;
import simpl.interpreter.Env;
import simpl.interpreter.FunValue;
import simpl.interpreter.IntValue;
import simpl.interpreter.PairValue;
import simpl.interpreter.RecValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.interpreter.lib.fst;
import simpl.interpreter.lib.hd;
import simpl.interpreter.lib.snd;
import simpl.interpreter.lib.tl;
import simpl.interpreter.pcf.iszero;
import simpl.interpreter.pcf.pred;
import simpl.interpreter.pcf.succ;
import simpl.parser.Symbol;
import simpl.typing.ArrowType;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.typing.TypeVar;

public class App extends BinaryExpr {

    public App(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " " + r + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        //System.out.println(this.toString());
        TypeResult l_type = l.typecheck(E);
        Substitution sub = l_type.s;
        TypeVar a = new TypeVar(true);
        TypeVar b = new TypeVar(true);
        sub = l_type.t.unify(new ArrowType(a, b)).compose(sub);
        TypeResult r_type = r.typecheck(sub.compose(E));
        sub = r_type.s.compose(sub);
        Type ra = sub.apply(a);
        //System.out.println("In App.java: type of a is: " + ra);
        sub = r_type.t.unify(ra).compose(sub);
        Type rb = sub.apply(b);
        //System.out.println("In App.java: type of b is: " + rb);
        return TypeResult.of(sub, rb);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        //FunValue l_v = (FunValue) l.eval(s);
        Value l_tmp_v = l.eval(s);
        Value r_v = r.eval(s);
        if (l_tmp_v instanceof FunValue) {
            FunValue l_v = (FunValue) l_tmp_v;
            if (l_v instanceof succ) {
                return new IntValue(((IntValue) r_v).n + 1);
            } else if (l_v instanceof pred) {
                return new IntValue(((IntValue) r_v).n - 1);
            } else if (l_v instanceof iszero) {
                return new BoolValue(((IntValue) r_v).n == 0);
            } else if (l_v instanceof fst) {
                return ((PairValue)r_v).v1;
            } else if (l_v instanceof snd) {
                return ((PairValue)r_v).v2;
            } else if (l_v instanceof hd) {
                return ((ConsValue) r_v).v1;
            } else if (l_v instanceof tl) {
                return ((ConsValue) r_v).v2;
            }
            return l_v.e.eval(State.of(new Env(l_v.E, l_v.x, r_v), s.M, s.p));
        } else if (l_tmp_v instanceof RecValue) {
            RecValue l_v = (RecValue) l_tmp_v;
            return new App(l_v.e, r).eval(s);
        } else {
            return null;
        }
        
    }
}
