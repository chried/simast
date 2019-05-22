package com.simast.base.params;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 定义controller传输单个id情况.
 *
 * @author chried
 */
public class IdForm implements Serializable {

    /**
     * 定义主键id.
     */
    @NotNull(message = "ID必传")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
