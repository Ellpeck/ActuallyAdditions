package ellpeck.gemification;

public class GemType {

    public int ID;
    public String name;

    public GemType(int ID, String name, boolean shouldAddToList){
        this.ID = ID;
        this.name = "fluid" + name;
        if(shouldAddToList) Util.gemList.add(ID, this);
    }
}
