package com.klaus.censor;

import java.util.List;

/**
 * 敏感字过滤器
 * Created by KlausZ on 2020/8/7.
 */
public class CensorWordFilter {

    private final ACTree tree;

    private CensorWordFilter() {
        this.tree = new ACTree();
    }

    public boolean isSensitive(String text) {
        return !tree.match(text).isEmpty();
    }

    public String replaceSensitive(String text, String replacement) {
        List<String> matched = tree.match(text);
        for (String s : matched) {
            text = text.replace(s, replacement);
        }
        return text;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private final CensorWordFilter filter;

        private Builder() {
            this.filter = new CensorWordFilter();
        }

        public Builder addWord(String word) {
            filter.tree.insert(word);
            return this;
        }

        public CensorWordFilter build() {
            filter.tree.buildFailNode();
            return filter;
        }
    }

}
