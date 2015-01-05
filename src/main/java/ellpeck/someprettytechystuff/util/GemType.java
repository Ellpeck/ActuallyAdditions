package ellpeck.someprettytechystuff.util;

public class GemType{

    public final int ID;
    public final String name;

    public GemType(int ID, String name, boolean shouldAddToList){
        this.ID = ID;
        this.name = "fluid" + name;
        if(shouldAddToList) Util.gemList.add(ID, this);
    }
}
