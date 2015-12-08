package se.hkr.activeageing.server.viewmodels;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * ZipCode vewModel
 * Created by Andreas Svensson & Daniel Ryhle on 2015-11-109.
 */
@XmlRootElement
public class ZipCodeViewModel {
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
