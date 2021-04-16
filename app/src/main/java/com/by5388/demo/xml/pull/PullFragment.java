package com.by5388.demo.xml.pull;

import com.by5388.demo.xml.BaseXmlFragment;
import com.by5388.demo.xml.XmlHelper;

/**
 * @author Administrator  on 2021/4/15.
 */
public class PullFragment extends BaseXmlFragment {

    @Override
    protected XmlHelper createXmlHelper() {
        return new PullHelper();
    }
}
