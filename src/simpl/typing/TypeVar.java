package simpl.typing;

import simpl.parser.Symbol;

public class TypeVar extends Type {

    private static int tvcnt = 0;

    private boolean equalityType;
    private Symbol name;

    public TypeVar(boolean equalityType) {
        this.equalityType = equalityType;
        if (tvcnt == 16) {
            System.out.println("here!");
        }
        name = Symbol.symbol("tv" + ++tvcnt);
    }

    @Override
    public boolean isEqualityType() {
        return equalityType;
    }

    @Override
    public Substitution unify(Type t) throws TypeCircularityError {
        System.out.println("TypeVar::unify");
        System.out.println(this);
        System.out.println(t);
        if (t instanceof TypeVar) {
            if(((TypeVar)t).name == name) {
                System.out.println("TypeVar::case 1");
                return Substitution.IDENTITY;
            }
            else {
                System.out.println("TypeVar::case 2");
                return Substitution.of(this, t);
            }
                
        }
        else if(t.contains(this))
            throw new TypeCircularityError();
        else {
            System.out.println("TypeVar::case 3");
            return Substitution.of(this, t);
        }
            
    }

    public String toString() {
        return "" + name;
    }

    @Override
    public boolean contains(TypeVar tv) {
        return tv.name == name;
    }

    @Override
    public Type replace(TypeVar a, Type t) {
        if (a.name == name) {
            return t;
        }
        return this;
    }
}
