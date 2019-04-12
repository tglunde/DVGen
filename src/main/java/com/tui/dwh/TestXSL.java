package com.tui.dwh;

import net.sf.saxon.Configuration;
import net.sf.saxon.Transform;

public class TestXSL {
    public static void main(String[] args) throws Exception {
        String[] arglist = {"-xsl:src/main/resources/wf.xsl" , "-it:main", "-o:workflows.xml"};
        Transform.main(arglist);
    }
}
