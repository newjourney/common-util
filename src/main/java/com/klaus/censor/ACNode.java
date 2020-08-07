package com.klaus.censor;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by KlausZ on 2020/8/7.
 */
class ACNode {
    char data; // 节点字符值
    String string; // 根节点到当前节点的字符值组合成的字符串
    Map<Character, ACNode> children = new HashMap<>();
    ACNode fail; // 失败指针：匹配失败时指向此节点，无需再从根节点开始匹配
    boolean isEnding; // 结束节点标记

    public ACNode(char data) {
        this.data = data;
        this.string = String.valueOf(data);
    }

    public ACNode(char data, String string) {
        this.data = data;
        this.string = string;
    }

}
