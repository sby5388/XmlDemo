package com.by5388.demo.xml.sax;

import com.by5388.demo.xml.BaseXmlFragment;
import com.by5388.demo.xml.XmlHelper;

/**
 * @author Administrator  on 2021/4/15.
 */
public class SaxFragment extends BaseXmlFragment {

    @Override
    protected XmlHelper createXmlHelper() {
        return new SaxHelper();
    }

}
