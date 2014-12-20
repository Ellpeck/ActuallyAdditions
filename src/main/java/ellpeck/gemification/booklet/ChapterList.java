package ellpeck.gemification.booklet;

import java.util.ArrayList;

public class ChapterList{

    public static ArrayList<Chapter> chapterList = new ArrayList<Chapter>();

    public static void init(){
        chapterList.add(new Chapter(0, "testChapterOne", 2, false));
        chapterList.add(new Chapter(1, "testChapterTwo", 3, false));
        chapterList.add(new Chapter(2, "testChapterThree", 2, false));
    }
}
