package simpl.typing;

import simpl.parser.Symbol;

public class DefaultTypeEnv extends TypeEnv {

    private TypeEnv E;

    public DefaultTypeEnv() {
        //hd tl fst snd TBD
        TypeVar a = new TypeVar(true);
        TypeVar b = new TypeVar(true);
        TypeVar c = new TypeVar(true);
        TypeVar d = new TypeVar(true);
        TypeVar e = new TypeVar(true);
        TypeVar f = new TypeVar(true);
        E = TypeEnv.of(E, Symbol.symbol("iszero"), new ArrowType(Type.INT, Type.BOOL));
        E = TypeEnv.of(E, Symbol.symbol("pred"), new ArrowType(Type.INT, Type.INT));
        E = TypeEnv.of(E, Symbol.symbol("succ"), new ArrowType(Type.INT, Type.INT));
        E = E.of(E, Symbol.symbol("fst"), new ArrowType(new PairType(a,b),a));
        E = E.of(E, Symbol.symbol("snd"), new ArrowType(new PairType(c,d),d));
        E = E.of(E, Symbol.symbol("hd"), new ArrowType(new ListType(e),e));
        E = E.of(E, Symbol.symbol("tl"), new ArrowType(new ListType(a),new ListType(a)));
    }

    @Override
    public Type get(Symbol x) {
        return E.get(x);
    }
}
