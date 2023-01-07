package org.example.yiji.dolphin.model.VO;

import org.example.yiji.dolphin.model.primary.Function;
import org.example.yiji.dolphin.model.primary.Permission;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Author wanglin
 * @Date 2021/1/26 14:56
 */
@Data
public class FunctionTreeVo {

    private String title;
    private String key;
    private List<FunctionTreeVo> children;

    public FunctionTreeVo() {
    }

    public FunctionTreeVo(Function function) {
        this.title = function.getName();
        StringBuilder sb = new StringBuilder();
        this.key = function.getKey();
        Set<Function> cps = function.getChildFunctions();
        if(!cps.isEmpty()){
            List<FunctionTreeVo> list = new ArrayList<>();
            for (Function cp : cps) {
                list.add(new FunctionTreeVo(cp));
            }
            this.children = list;
        }
    }

}
