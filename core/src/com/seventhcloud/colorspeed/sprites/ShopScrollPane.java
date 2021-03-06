package com.seventhcloud.colorspeed.sprites;

import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.seventhcloud.colorspeed.items.Item;
import com.seventhcloud.colorspeed.states.MenuState;

/**
 * Created by WhiteHope on 21.10.2016.
 */
public class ShopScrollPane extends ScrollPane {

    private Table scrollTable;
    private float middlePointX;
    private int pageSpacing = 20;
    private float defaultCellSize;
    private float minDistance = 0;
    private float minusScrollX;
    private Cell cell;
    private MenuState.Shop shop;

    public ShopScrollPane(MenuState.Shop shop) {
        super(null);
        this.shop = shop;
        middlePointX = shop.getShopSlot().getX() + shop.getShopSlot().getWidth() / 2;
        defaultCellSize = shop.getShopSlot().getWidth() / 2;
        scrollTable = new Table().defaults().padRight(shop.getShopSlot().getWidth()).size(defaultCellSize).getTable();
        scrollTable.padLeft(shop.getShopSlot().getX() + shop.getShopSlot().getWidth()).padRight(shop.getShopSlot().getWidth() / 2 + scrollTable.defaults().getPrefWidth() / 2);
        super.setWidget(scrollTable);
        minusScrollX = middlePointX - defaultCellSize / 2;
    }



    private void initialize() {


        for (Cell cell : scrollTable.getCells()) {
            cell.space(pageSpacing);
        }
        cell = scrollTable.getCells().get(0);
    }

    public void addItems(Array<Item> items) {
        for (Item item : items) {
            scrollTable.add(item).right();

        }
        initialize();
    }

    public void act(float delta) {
        super.act(delta);

        if (isUntouched()) {

            minDistance = scrollTable.getWidth();

            for (Cell cell : scrollTable.getCells()) {

                if (Math.abs((cell.getActorX() - getScrollX()) - minusScrollX) < minDistance) {

                    minDistance = Math.abs((cell.getActorX() - getScrollX()) - minusScrollX);
                    this.cell = cell;
                }
            }
            scrollX(cell.getActorX() - minusScrollX);
        }


    }

    public void deleteItemOnSlot(){
        scrollTable.getChildren().removeIndex(cell.getActor().getZIndex());
        scrollTable.layout();
    }

    public boolean isUntouched(){
        if (!isFlinging() && !isDragging() && !isPanning()) return true;
        return false;
    }

    public Item getItemOnShopSlot(){
        return (Item) cell.getActor();
    }
}
