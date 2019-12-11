import java.util.*;

/**
 * The Sym class defines a symbol-table entry. 
 * Each Sym contains a type (a Type).
 */
public class Sym {
    private Type type;
    private int offset;

    public Sym(Type type) {
        this.type = type;
    }
    
    public Sym(Type type, int offset) {
        this.type = type;
        this.offset = offset;
    }
    public int getOffest(){
        return offset;
    }
    public boolean isGlobal(){
        if (this.offset > 0){
            return true;
        }
        else{
            return false;
        }
    }
    public Type getType() {
        return type;
    }
    
    public String toString() {
        return type.toString();
    }
}

/**
 * The FnSym class is a subclass of the Sym class just for functions.
 * The returnType field holds the return type and there are fields to hold
 * information about the parameters.
 */
class FnSym extends Sym {
    // new 
    private int offset = 0;
    private Type returnType;
    private int numParams;
    private List<Type> paramTypes;
    private int localsSize = 0;
    private int paramsSize = 0;
    
    public FnSym(Type type, int numparams) {
        super(new FnType());
        returnType = type;
        numParams = numparams;
    }

    public void addFormals(List<Type> L) {
        paramTypes = L;
    }

    public void addLocal(VarDeclNode v){
        Type type =  v.getType();
        if(type.isIntType()){
            localsSize += 4;
        }
        else if(type.isBoolType()){
            localsSize += 1;
        }
        else if(type.isVoidType()){
            localsSize += 4;
        }
        else if(type.isStringType()){
            localsSize += 4;
        }
    }

    public void addParams(List<Type> types){
        for(Type type : types){
            int size = 0;
            if(type.isIntType()){
                size = 4;
            }
            else if(type.isBoolType()){
                size = 1;
            }
            else if(type.isVoidType()){
                size = 4;
            }
            else if(type.isStringType()){
                size = 4;
            }
            paramsSize += size;
        }
    }
    
    public Type getReturnType() {
        return returnType;
    }

    public int getNumParams() {
        return numParams;
    }

    public List<Type> getParamTypes() {
        return paramTypes;
    }

    public boolean isGlobal(){
        if (this.offset > 0){
            return true;
        }
        else{
            return false;
        }
    }

    public int getLocalsSize() {
        return localsSize;
    }

    public int getParamsSize() {
        return paramsSize;
    }

    public String toString() {
        // make list of formals
        String str = "";
        boolean notfirst = false;
        for (Type type : paramTypes) {
            if (notfirst)
                str += ",";
            else
                notfirst = true;
            str += type.toString();
        }

        str += "->" + returnType.toString();
        return str;
    }
}

/**
 * The StructSym class is a subclass of the Sym class just for variables 
 * declared to be a struct type. 
 * Each StructSym contains a symbol table to hold information about its 
 * fields.
 */
class StructSym extends Sym {
    // new fields
    private IdNode structType;  // name of the struct type
    
    public StructSym(IdNode id) {
        super(new StructType(id));
        structType = id;
    }

    public IdNode getStructType() {
        return structType;
    }    
}

/**
 * The StructDefSym class is a subclass of the Sym class just for the 
 * definition of a struct type. 
 * Each StructDefSym contains a symbol table to hold information about its 
 * fields.
 */
class StructDefSym extends Sym {
    // new fields
    private SymTable symTab;
    
    public StructDefSym(SymTable table) {
        super(new StructDefType());
        symTab = table;
    }

    public SymTable getSymTable() {
        return symTab;
    }
}
