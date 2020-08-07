package com.klaus.censor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 基于AC自动机的字典树
 * 通过多模式串匹配实现敏感词过滤
 * Created by KlausZ on 2020/8/7.
 */
public class ACTree {

    private final ACNode root = new ACNode('/'); // 根节点

    public void insert(String text) {
        insert(text.trim().toCharArray());
    }

    public void insert(char[] text) {
        ACNode p = root;
        StringBuilder s = new StringBuilder();
        for (char c : text) {
            s.append(c);
            if (p.children.get(c) == null) {
                ACNode newNode = new ACNode(c, s.toString());
                p.children.put(c, newNode);
            }
            p = p.children.get(c);
        }
        p.isEnding = true;
    }

    public List<String> match(String text) {
        return match(text.toCharArray());
    }

    public List<String> match(char[] text) {
        List<String> ret = new ArrayList<>();
        ACNode p = root;
        for (char ci : text) {
            while (p.children.get(ci) == null && p != root) {
                p = p.fail; // 失败指针在这里发挥作用
            }
            p = p.children.get(ci);
            if (p == null) p = root; // 如果没有匹配的，从root开始重新匹配
            ACNode tmp = p;
            while (tmp != root) {
                if (tmp.isEnding) {
                    ret.add(tmp.string);
                }
                tmp = tmp.fail;
            }
        }
        return ret;
    }

    /**
     * 广度优先遍历，构建失败节点
     */
    public void buildFailNode() {
        Queue<ACNode> queue = new LinkedList<>();
        root.fail = null;
        queue.add(root);
        while (!queue.isEmpty()) {
            ACNode a = queue.remove();
            for (ACNode c : a.children.values()) {
                if (a == root) {
                    c.fail = root;
                } else {
                    ACNode f = a.fail;
                    while (f != null) {
                        ACNode fc = f.children.get(c.data);
                        if (fc != null) {
                            c.fail = fc;
                            break;
                        }
                        f = f.fail;
                    }
                    if (f == null) {
                        c.fail = root;
                    }
                }
                queue.add(c);
            }
        }
    }

    @Override
    public String toString() {
        // TODO
        return super.toString();
    }


}
