package models.things.tools;

import models.Point;
import models.Result;
import models.enums.Quality;
import models.things.Item;

public class TrashCan extends Tool {
    private Quality quality;
    @Override
    public Result useTool(Tool tool, Point point) {
        super.useTool(tool, point);
        return null;
    }

}
