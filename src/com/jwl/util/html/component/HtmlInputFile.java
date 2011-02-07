package com.jwl.util.html.component;

import javax.faces.component.html.HtmlInputText;

import com.jwl.util.html.renderer.RendererTypes;

public class HtmlInputFile extends HtmlInputText {

    public HtmlInputFile() {
        super();
        setRendererType(RendererTypes.INPUT_FILE_RENDERER);
    }
    
}
