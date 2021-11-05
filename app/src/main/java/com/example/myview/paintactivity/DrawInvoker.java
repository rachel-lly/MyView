package com.example.myview.paintactivity;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class DrawInvoker {

    private List<DrawPath> drawPathList = Collections.synchronizedList(new ArrayList<>());

    private List<DrawPath> redoList = Collections.synchronizedList(new ArrayList<>());

    public void add(DrawPath drawPath){
        redoList.clear();
        drawPathList.add(drawPath);
    }

    public void undo(){
        if(drawPathList.size()>0){
            DrawPath undo = drawPathList.get(drawPathList.size() - 1);
            drawPathList.remove(drawPathList.size() - 1);
            undo.undo();
            redoList.add(undo);
        }
    }

    public void redo(){
        if(drawPathList.size()>0){
            DrawPath redoCommand = redoList.get(drawPathList.size() - 1);
            redoList.remove(drawPathList.size() - 1);
            drawPathList.add(redoCommand);
        }
    }

    public void execute(Canvas canvas){
        if(drawPathList != null){
            for(DrawPath drawPath: drawPathList){
                drawPath.draw(canvas);
            }
        }
    }

    public boolean canRedo(){
        return redoList.size() > 0;
    }

    public boolean canUndo(){
        return drawPathList.size() > 0;
    }

}
