/*
 * This file ("PageLinkButton.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet.page;

import de.ellpeck.actuallyadditions.mod.booklet.BookletUtils;

public class PageLinkButton extends PageButton{

    private final String link;

    public PageLinkButton(int id, String link){
        super(id);
        this.link = link;
    }

    @Override
    public boolean onAction(){
        BookletUtils.openBrowser(this.link);
        return true;
    }
}
