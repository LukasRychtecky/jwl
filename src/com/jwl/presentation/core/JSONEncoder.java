package com.jwl.presentation.core;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Lukas Rychtecky
 */
public class JSONEncoder {
	
	protected Map<String, String> map;
	protected List<String> list;
	private Boolean useList = Boolean.FALSE;

	public JSONEncoder(List<String> list) {
		this.list = list;
		this.useList = Boolean.TRUE;
	}

	public JSONEncoder(Map<String, String> map) {
		this.map = map;
	}
	
	
	public Iterator<String> keys() {
		if (useList) {
			return this.list.iterator();
		}
        return this.map.keySet().iterator();
    }
	
	@Override
	public String toString() {
        try {
			StringBuilder sb;
			if (this.useList) {
				sb = new StringBuilder("[");
			} else {
				sb = new StringBuilder("{");
			}
            Iterator<String> keys = this.keys();
            

            while (keys.hasNext()) {
                if (sb.length() > 1) {
                    sb.append(',');
                }
				Object o = keys.next();
				if (this.useList) {
					sb.append(quote(o.toString()));
				} else {
					sb.append(quote(o.toString()));
					sb.append(':');
					sb.append(quote(this.map.get(o).toString()));
					
				}
            }
            
			if (this.useList) {
				sb.append(']');
			} else {
				sb.append('}');
			}
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }
	
	public static String quote(String string) {
        if (string == null || string.length() == 0) {
            return "\"\"";
        }

        char b;
        char c = 0;
        String hexValue;
        int i;
        int len = string.length();
        StringBuilder sb = new StringBuilder(len + 4);

        sb.append('"');
        for (i = 0; i < len; i += 1) {
            b = c;
            c = string.charAt(i);
            switch (c) {
            case '\\':
            case '"':
                sb.append('\\');
                sb.append(c);
                break;
            case '/':
                if (b == '<') {
                    sb.append('\\');
                }
                sb.append(c);
                break;
            case '\b':
                sb.append("\\b");
                break;
            case '\t':
                sb.append("\\t");
                break;
            case '\n':
                sb.append("\\n");
                break;
            case '\f':
                sb.append("\\f");
                break;
            case '\r':
                sb.append("\\r");
                break;
            default:
                if (c < ' ' || (c >= '\u0080' && c < '\u00a0') ||
                               (c >= '\u2000' && c < '\u2100')) {
                    hexValue = "000" + Integer.toHexString(c);
                    sb.append("\\u" + hexValue.substring(hexValue.length() - 4));
                } else {
                    sb.append(c);
                }
            }
        }
        sb.append('"');
        return sb.toString();
    }

	
}
