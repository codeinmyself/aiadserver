package com.xmu.aiad.query;


import com.xmu.aiad.util.BaseConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PoemWhite on 16/6/6.
 */
public class PageSelect extends Select{

    final private Select wrappedSelect;

    final private Page page;

    public PageSelect(Select select, Page page) {
        super(select.getSql(), select.getParameters());

        String sql = getSql();
        sql = String.format("%1$s %2$s", getSql(), "limit ? , ? ");

        setSql(sql);

        List<Object> parameters = new ArrayList<Object>();
        for (Object parameter : getParameters()) {
            parameters.add(parameter);
        }

        int start = page.getStart();
        int limit = page.getLimit();

        if(start<0){
            start = BaseConstant.PAGINATE_START_DEFAULT;
        }
        parameters.add(start);

        if(limit<0){
            limit = BaseConstant.PAGINATE_LIMIT_DEFAULT;
        }
        parameters.add(limit);

        setParameters(parameters.toArray());

        wrappedSelect = select;
        this.page = page;
    }

    public Select getWrappedSelect() {
        return wrappedSelect;
    }

    public Page getPage() {
        return page;
    }
}
