package com.oim.icepouring.thread;

import java.util.List;
import java.util.TimerTask;

public class ErrorViewer extends TimerTask {

    public List<List<? extends  String>> list;
    int indexColumn = 0;
    int indexLine = 0;
    @Override
    public void run()
    {
            if(indexColumn == list.size())
                indexColumn = 0;
            if(indexLine == list.get(indexColumn).size()) {
                indexColumn++;
                indexLine = 0;
            }
            String fuck = list.get(indexColumn).get(indexLine++);
            for(; indexColumn < list.size();indexColumn++)
            {
                for( ; indexLine<list.get(indexColumn).size(); indexLine++)
                {
                    String s = list.get(indexColumn).get(indexLine);
                    break;
                }
            }
    }

    public void setUnitErrorViewer(List<List<? extends String>> list)
    {
        this.list = list;
    }

}
