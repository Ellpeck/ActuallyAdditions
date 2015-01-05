package ellpeck.someprettytechystuff.booklet;

import java.util.ArrayList;

public class ChapterList{

    public static ArrayList<Chapter> chapterList = new ArrayList<Chapter>();

    public static void init(){
        chapterList.add(new Chapter(0, "crucible", 1, false));
        chapterList.add(new Chapter(1, "crucibleFire", 2, false));
        chapterList.add(new Chapter(2, "gems", 4, false));
    }
}
