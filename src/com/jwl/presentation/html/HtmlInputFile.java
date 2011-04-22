package com.jwl.presentation.html;

import javax.faces.component.html.HtmlInputText;

public class HtmlInputFile extends HtmlInputText {

    public HtmlInputFile() {
        super();
        setRendererType("com.jwl.html.InputFileRenderer");
    }
    
}
