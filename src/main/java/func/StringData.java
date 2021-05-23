package func;

import ec.gp.GPData;

public class StringData extends GPData {
    // return value
    public String str;

    // you can add more data fields; add them also to copyTo

    public void copyTo(final GPData gpd) {
        // copy my stuff to another StringData
        ((StringData)gpd).str = str;
    }

}

